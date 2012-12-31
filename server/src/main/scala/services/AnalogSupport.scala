package services

import models._

import com.phidgets._
import com.phidgets.AnalogPhidget
import com.phidgets.event._

trait AnalogSupport {
	println(Phidget.getLibraryVersion())

	val analog = new AnalogPhidget
	var analogAttached = false

	def bitsToVoltage(input: Double): Double = input.toDouble * 1000.0 / 4095.0

	def getAnalogInputs() = {
		if(!analogAttached) initAnalog()
		(for(i <- (0 until 8))
			yield AnalogIO(i, bitsToVoltage(analog.getVoltage(i)), new java.util.Date, None)
		).toList
	}

	def setAnalogOutput(io: AnalogIO) = {
		if(!analogAttached) initAnalog()
		analog.setVoltage(io.position, io.value)
	}

	def initAnalog(): Unit = {
		analog.openAny()
		println("waiting for analog attachment...")
		analog.waitForAttachment()

		analog.addAttachListener(new AttachListener() {
			def attached(ae: AttachEvent) = {
				println("Analog attachment of " + ae)
				analogAttached = true
			}
		})
		analog.addDetachListener(new DetachListener() {
			def detached(ae: DetachEvent) = {
				println("Analog detachment of " + ae)
				analogAttached = false
			}
		})
		analog.addErrorListener(new ErrorListener() {
			def error(ee: ErrorEvent) = {
				println("Analog event for " + ee)
			}
		})

		println("Phidget Information")
		println("====================================");
		println("Version: " + analog.getDeviceVersion())
		println("Name: " + analog.getDeviceName())
		println("Serial #: " + analog.getSerialNumber())
		println("# Analog Outputs: " + analog.getOutputCount())

		println("Voltage Min: " + analog.getVoltageMin(0))
		println("Voltage Max: " + analog.getVoltageMax(3) + "\n")

		(0 until analog.getOutputCount).foreach(i => {
			analog.setEnabled(i, true)
		})

		// analog.getVoltage

    analogAttached = true
	}

	def disconnectAnalog = {
		analogAttached = false
		analog.close()
	}
}