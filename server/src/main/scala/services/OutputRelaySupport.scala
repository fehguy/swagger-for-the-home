package services

import models._
import config._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

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

	def setRelayOutput(io: DigitalIO) = {
		OutputRelaySupport.relay.setOutputState(io.position, io.value)
		ApiResponse("set output on " + io.position + " to " + io.value, 200)
	}

	def getRelayOutput(position: Int) = {
		DigitalIO(None, position, OutputRelaySupport.relay.getOutputState(position))
	}

	def getRelayOutputs: List[DigitalIO]= {
		(for(position <- 0 to 7) yield {
			getRelayOutput(position)
		}).toList
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
				relay.open(e.toInt)
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