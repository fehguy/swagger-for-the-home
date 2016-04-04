package service

import model._
import config._

import com.phidgets._
import com.phidgets.event._

import java.util.Date

import akka.actor._
import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit

import scala.collection.mutable.HashMap

object OutputRelaySupport {
  val relays: HashMap[String, InterfaceKitPhidget] = HashMap.empty
  val attachmentState = new HashMap[String, Boolean]()
}

trait OutputRelaySupport {
  val system = ActorSystem("RelaySupportScheduler")
  val relayTimers: HashMap[Int, Long] = new HashMap[Int, Long]

  import system.dispatcher
  val relayDeviceIdMap: Map[String, List[InputZone]] = Configurator.inputZones.groupBy(_.outputDeviceId)

  initRelay(relayDeviceIdMap.keySet)

  var outputRelayCancellable: Option[Cancellable] = None

  def startRelayUpdate = {
    outputRelayCancellable = Some(system.scheduler.schedule(15 seconds, Duration.create(30, TimeUnit.SECONDS), new Runnable {
      def run() = {
        relayTimers.map(m => {
          if (m._2 < System.currentTimeMillis) {
            setOutputRelay(false, m._1)
            relayTimers.remove(m._1)
            println("shut off switch for " + m._1)
          } else
            println("not time for switch " + m._1)
        })
      }
    }))
  }

  def setAllRelayOutput(state: Boolean) = {
    (for (zone <- relayDeviceIdMap.values.flatten) yield {
      setOutputRelay(state, zone.logicalPosition)
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
    relayDeviceIdMap.values.flatten.filter(_.logicalPosition == logicalPosition).toList match {
      case e if (e.size == 1) => {
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
        None
      }
    }
  }

  def getRelayOutputs = {
    (for (position <- 0 to 15) yield {
      getRelayOutput(position)
    }).toList
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

  def relayOnWithTimer(name: String, timer: Int) = {
    val ids: Set[Int] = name match {
      case "basement" => Set(1, 2, 3, 8)
      case "middle-floor" => Set(4, 6, 7, 9, 12)
      case "upstairs" => Set(10, 11)
      case _ => Set()
    }
    for (i <- ids) {
      setOutputRelay(true, i)
      relayTimers += i -> (System.currentTimeMillis + (timer * 60000))
    }
  }

  def disconnectRelay = {
    for (relay <- OutputRelaySupport.relays.values) relay.close()
  }
}