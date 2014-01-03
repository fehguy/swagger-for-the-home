package service

import model._
import config._

import com.phidgets._
import com.phidgets.event._

import java.util.Date

import scala.collection.mutable.HashMap

object OutputRelaySupport {
  val relays: HashMap[String, InterfaceKitPhidget] = HashMap.empty
  val attachmentState = new HashMap[String, Boolean]()
}

trait OutputRelaySupport {
  println("really!")
  val relayDeviceIdMap: Map[String, List[InputZone]] = Configurator.inputZones.groupBy(_.outputDeviceId)

  initRelay(relayDeviceIdMap.keySet)

  def setAllRelayOutput(state: Boolean) = {
    (for (zone <- relayDeviceIdMap.values.flatten) yield {
      setOutputRelay(state, zone.position)
    }).toList
  }

  def setOutputRelay(state: Boolean, logicalPosition: Int) = {
    println("set relay output on " + logicalPosition)
    relayDeviceIdMap.values.flatten.filter(_.logicalPosition == logicalPosition).toList match {
      case e if (e.size == 1) => {
        val zone = e.head
        val relay = OutputRelaySupport.relays(zone.outputDeviceId)
        if (!relay.isAttached) {
          println("waiting for relay id " + zone.outputDeviceId + " to attach")
          relay.waitForAttachment(10000)
        }
        relay.setOutputState(zone.position, state)
      }
      case _ =>
    }
    ApiResponse("set output on " + logicalPosition + " to " + state, 200)
  }

  def getRelayOutput(logicalPosition: Int): Option[DigitalIO] = {
    println("getting relay for " + logicalPosition)
    relayDeviceIdMap.values.flatten.filter(_.logicalPosition == logicalPosition).toList match {
      case e if (e.size == 1) => {
        println(e)
        val zone = e.head
        val relay = OutputRelaySupport.relays(zone.outputDeviceId)

        if (!relay.isAttached) {
          println("waiting for relay id " + zone.outputDeviceId + " to attach")
          relay.waitForAttachment(10000)
        }
        Some(DigitalIO(
          position = logicalPosition,
          value = relay.getOutputState(zone.position),
          timestamp = Some(new Date),
          name = None))
      }
      case _ => {
        println("none found")
        None
      }
    }
  }

  def getRelayOutputs = {
    Configurator.hasConfig("relay") match {
      case true =>
        (for (position <- 0 to 7) yield {
          getRelayOutput(position)
        }).toList
      case false => List()
    }
  }

  def initRelay(ids: Set[String]): Map[String, InterfaceKitPhidget] = {
    (for (id <- ids.filter(_ != "")) yield {
      println("initializing id " + id)
      val relay = new InterfaceKitPhidget

      println("1: initializing id " + id + ", " + relay + ", status: " + !relay.isAttached)

      if (Configurator.hasConfig("remote"))
        relay.open(id.toInt, Configurator("remote"), 5001)
      else {
        println("opening local relay")
        relay.open(id.toInt)
      }

      relay.waitForAttachment
      println("2: initializing id " + id + ", " + relay + ", status: " + !relay.isAttached)

      relay.addAttachListener(new AttachListener() {
        def attached(ae: AttachEvent) = {
          OutputRelaySupport.attachmentState += id -> true
          println("Relay " + id + " attachment of " + ae)
        }
      })
      relay.addDetachListener(new DetachListener() {
        def detached(ae: DetachEvent) = {
          OutputRelaySupport.attachmentState += id -> false
          println("Relay " + id + " detachment of " + ae)
        }
      })
      relay.addErrorListener(new ErrorListener() {
        def error(ee: ErrorEvent) = {
          println("Relay " + id + " " + ee)
        }
      })

      OutputRelaySupport.relays += id -> relay
      (id, relay)
    }).toMap
  }

  def disconnectRelay = {
    for (relay <- OutputRelaySupport.relays.values) relay.close()
  }
}