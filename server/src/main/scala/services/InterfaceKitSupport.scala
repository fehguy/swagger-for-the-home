package services

import models._
import config._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

import scala.collection.mutable.HashMap

object InterfaceKitSupport {
  val ifks: HashMap[String, InterfaceKitPhidget] = HashMap.empty
  def add(id: String, ph: InterfaceKitPhidget) = {
    ifks += id -> ph
  }
  def apply(id: String) = {
    ifks(id)
  }
}

trait InterfaceKitSupport extends AnalogConversion {
  val interfaceKitDeviceIdMap: Map[String, List[InputZone]] = Configurator.inputZones.groupBy(_.deviceId)

  println("initializing InterfaceKitSupport support")

  initInterfaceKit(interfaceKitDeviceIdMap.keySet)

  def inputs() = interfaceKitDeviceIdMap.map(m => m._2).flatten.toList

  println("inputs: " + inputs)

  def getAnalogInputs() = {
    (for(input <- inputs())
      yield {
        try{
          Some(AnalogIO(new java.util.Date, 
            input.logicalPosition,
            Some(input.name),
            bitsToVoltage(getRawValue(input.deviceId, input.position))))
        }
        catch {
          case _ => None
        }
      }
    ).flatten.toList
  }

  def getRawValue(deviceId: String, position: Int): Int = {
    val inputBoard = InterfaceKitSupport(deviceId)

    try{
      if(!inputBoard.isAttached){
        println("not attached!  waiting...")
        inputBoard.waitForAttachment(10000)
        println("done waiting")
      }
      inputBoard.getSensorRawValue(position)
    }
    catch {
      case e: Exception => println("failed to get raw value for " + position + ", message: " + e.getMessage); throw e
    }
  }

  /**
   * sets the output value for the digital IO based on logical position
   **/
  def setDigitalOutput(io: DigitalIO) = {
    interfaceKitDeviceIdMap.map(m => {
      inputs.filter(input => input.logicalPosition == io.position).foreach(i => {
        InterfaceKitSupport(i.deviceId).setOutputState(io.position, io.value)
      })
    })
    ApiResponse("set output on " + io, 200)
  }

  /**
   * gets output state for the given logical position
   **/
  def getDigitalOutputState(logicalPosition: Int): DigitalIO = {
    (for(input <- inputs())
      yield DigitalIO(None, logicalPosition, InterfaceKitSupport(input.deviceId).getOutputState(input.position))
    ).head
  }

  /**
   * initializes all boards
   **/
  def initInterfaceKit(ids: Set[String]): Map[String, InterfaceKitPhidget] = {
    (for(id <- ids) yield {
      println("initializing id " + id)
      val ifk = new InterfaceKitPhidget

      println("1: initializing id " + id + ", " + ifk + ", status: " + !ifk.isAttached)

      ifk.open(id.toInt)
      ifk.waitForAttachment
      println("2: initializing id " + id + ", " + ifk + ", status: " + !ifk.isAttached)

      ifk.addAttachListener(new AttachListener() {
        def attached(ae: AttachEvent) = {
          // println("InterfaceKit " + id + " attachment of " + ae)
        }
      })
      ifk.addDetachListener(new DetachListener() {
        def detached(ae: DetachEvent) = {
          // println("InterfaceKit " + id + " detachment of " + ae)
        }
      })
      ifk.addErrorListener(new ErrorListener() {
        def error(ee: ErrorEvent) = {
          println("InterfaceKit " + id + " " + ee)
        }
      })

      InterfaceKitSupport.add(id, ifk)
      (id, ifk)
    }).toMap
  }

  def disconnectAnalog = {
    InterfaceKitSupport.ifks.map(m => m._2.close())
  }
}