package services

import models._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

trait InterfaceKitSupport {
	println(Phidget.getLibraryVersion())

	val ifk = new InterfaceKitPhidget
	var ifkAttached = false

	def bitsToVoltage(input: Int): Double = input.toDouble * 5.0 / 4095.0 * 100.0

	def getAnalogInputs() = {
		if(!ifkAttached) initIntefaceKit()
		(for(i <- (0 until 8))
			yield AnalogIO(i, bitsToVoltage(ifk.getSensorRawValue(i)))
		).toList
	}

	def setDigitalOutput(io: DigitalIO) = {
		if(!ifkAttached) initIntefaceKit()
		ifk.setOutputState(io.position, io.value)

		ApiResponse("set output on " + io, 200)
	}

	def getDigitalOutputState(position: Int) = {
		if(!ifkAttached) initIntefaceKit()
		DigitalIO(position, ifk.getOutputState(position))
	}

	def initIntefaceKit(): Unit = {
		ifk.openAny()
		println("waiting for interface kit attachment...")
		ifk.waitForAttachment()

		ifk.addAttachListener(new AttachListener() {
			def attached(ae: AttachEvent) = {
				println("attachment of " + ae)
				ifkAttached = true
			}
		})
		ifk.addDetachListener(new DetachListener() {
			def detached(ae: DetachEvent) = {
				println("detachment of " + ae)
				ifkAttached = false
			}
		})
		ifk.addErrorListener(new ErrorListener() {
			def error(ee: ErrorEvent) = {
				println("error event for " + ee)
			}
		})

		println("Phidget Information")
		println("====================================");
		println("Version: " + ifk.getDeviceVersion())
		println("Name: " + ifk.getDeviceName())
		println("Serial #: " + ifk.getSerialNumber())
		println("# Digital Outputs: " + ifk.getOutputCount())

		(0 until ifk.getOutputCount).foreach(i => {
			ifk.setOutputState(i, false)
		})

    ifkAttached = true
	}

	def disconnectAnalog = {
		ifkAttached = false
		ifk.close()
	}
}