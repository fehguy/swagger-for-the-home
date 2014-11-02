package service

import model._
import config._

import com.phidgets._
import com.phidgets.event._

import scala.collection.mutable.HashMap

object InterfaceKitSupport {
  val ifks: HashMap[String, InterfaceKitPhidget] = HashMap.empty
  val attachmentState = new HashMap[String, Boolean]()

  def add(id: String, ph: InterfaceKitPhidget) = {
    ifks += id -> ph
  }
  def apply(id: String) = {
    ifks(id)
  }
}

trait InterfaceKitSupport extends AnalogConversion {
  val interfaceKitDeviceIdMap: Map[String, List[InputZone]] = Configurator.inputZones.groupBy(_.inputDeviceId)

  var ikConnection = initInterfaceKit(interfaceKitDeviceIdMap.keySet)

  def inputs() = interfaceKitDeviceIdMap.map(m => m._2).flatten.toList

  var lock: AnyRef = new Object()

  def getAnalogInputs() = {
    (for (input <- inputs()) yield {
      try {
        Some(AnalogIO(
          position = input.logicalPosition,
          value = bitsToVoltage(getRawValue(input.inputDeviceId, input.position)),
          timestamp = new java.util.Date,
          name = Some(input.name)))
      } catch {
        case e: Exception => {
          e.printStackTrace
          None
        }
      }
    }
    ).flatten.toList
  }

  def getRawValue(deviceId: String, position: Int): Int = {
    val inputBoard = InterfaceKitSupport(deviceId)

    try {
      if (!inputBoard.isAttached) {
        inputBoard.waitForAttachment(10000)
      }
      inputBoard.getSensorRawValue(position)
    } catch {
      case e: Exception => {
        println("failed to get raw value for " + position + ", message: " + e.getMessage)
        throw e
      }
    }
  }

  /**
   * initializes all boards
   */
  def initInterfaceKit(ids: Set[String]): Map[String, InterfaceKitPhidget] = {
    (for (id <- ids) yield {
      val ifk = new InterfaceKitPhidget

      if (Configurator.hasConfig("remote"))
        ifk.open(id.toInt, Configurator("remote"), 5001)
      else {
        println("opening local ifk " + id)
        ifk.open(id.toInt)
      }

      ifk.waitForAttachment

      ifk.addAttachListener(new AttachListener() {
        def attached(ae: AttachEvent) = {
          InterfaceKitSupport.attachmentState += id -> true
        }
      })
      ifk.addDetachListener(new DetachListener() {
        def detached(ae: DetachEvent) = {
          InterfaceKitSupport.attachmentState += id -> false
        }
      })
      ifk.addErrorListener(new ErrorListener() {
        def error(ee: ErrorEvent) = {
        }
      })

      InterfaceKitSupport.add(id, ifk)
      (id, ifk)
    }).toMap
  }

  def resetAnalog = {
    try {
      InterfaceKitSupport.ifks.values.foreach(_.close())
    } catch {
      case e: Exception => e.printStackTrace
    }
    InterfaceKitSupport.ifks.clear

    initInterfaceKit(interfaceKitDeviceIdMap.keySet)
  }

  def disconnectAnalog = {
    InterfaceKitSupport.ifks.map(m => m._2.close())
  }
}