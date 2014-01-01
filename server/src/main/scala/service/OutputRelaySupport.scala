package service

import model._
import config._

import com.phidgets._
import com.phidgets.event._

import java.util.Date

import scala.collection.mutable.HashMap

object OutputRelaySupport {
  var relays: HashMap[String, InterfaceKitPhidget] = HashMap.empty
  var relay: InterfaceKitPhidget = new InterfaceKitPhidget
}

trait OutputRelaySupport {
  var relayAttached = {
    Configurator.hasConfig("relay") match {
      case true => initRelay
      case false => {
        println("relay disabled")
        false
      }
    }
  }

  def setAllRelayOutput(state: Boolean) = {
    Configurator.hasConfig("relay") match {
      case true =>
        (for(position <- 0 to 7) yield {
          setOutputRelay(state, position)
        }).toList
      case false => List()
    }
  }

  def setOutputRelay(state: Boolean, position: Int) = {
    OutputRelaySupport.relay.setOutputState(position, state)
    ApiResponse("set output on " + position + " to " + state, 200)
  }

  def getRelayOutput(position: Int) = {
    DigitalIO(
      position = position, 
      value = OutputRelaySupport.relay.getOutputState(position), 
      timestamp = Some(new Date),
      name = None)
  }

  def getRelayOutputs: List[DigitalIO]= {
    Configurator.hasConfig("relay") match {
      case true =>
        (for(position <- 0 to 7) yield {
          getRelayOutput(position)
        }).toList
      case false => List()
    }
  }

  def initRelay(): Boolean = {
    val relay = OutputRelaySupport.relay
    Configurator("relay") match {
      case "" => {
        println("waiting for output relay attachment (default) ...")
        relay.openAny()
      }
      case e:String => {
        println("waiting for output relay attachment (id " + e + ") ...")
        relay.open(e.toInt, Configurator("remote"), 5001)
      }
    }

    relay.waitForAttachment()

    relay.addAttachListener(new AttachListener() {
      def attached(ae: AttachEvent) = {
        // println("Output relay attachment of " + ae)
        relayAttached = true
      }
    })
    relay.addDetachListener(new DetachListener() {
      def detached(ae: DetachEvent) = {
        // println("Output relay detachment of " + ae)
        relayAttached = false
      }
    })
    relay.addErrorListener(new ErrorListener() {
      def error(ee: ErrorEvent) = {
        println("Output relay error event for " + ee)
      }
    })
    println("Relay serial #: " + relay.getSerialNumber())
    true
  }

  def disconnectRelay = {
    OutputRelaySupport.relay.close()
  }
}