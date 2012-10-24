package services

import models._
import config._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

trait OutputRelaySupport {
	val relay = new InterfaceKitPhidget
	var relayAttached = false

	def setRelayOutput(io: DigitalIO) = {
		if(!relayAttached) initRelay()
		relay.setOutputState(io.position, io.value)

		ApiResponse("set output on " + io.position + " to " + io.value, 200)
	}

	def getRelayOutput(position: Int) = {
		if(!relayAttached) initRelay()
		DigitalIO(position, relay.getOutputState(position))
	}

	def initRelay(): Unit = {
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
				println("attachment of " + ae)
				relayAttached = true
			}
		})
		relay.addDetachListener(new DetachListener() {
			def detached(ae: DetachEvent) = {
				println("detachment of " + ae)
				relayAttached = false
			}
		})
		relay.addErrorListener(new ErrorListener() {
			def error(ee: ErrorEvent) = {
				println("error event for " + ee)
			}
		})

    relayAttached = true
	}

	def disconnectRelay = {
		relayAttached = false
		relay.close()
	}
}