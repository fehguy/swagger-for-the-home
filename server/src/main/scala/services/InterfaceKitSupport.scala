package services

import models._
import config._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

trait InterfaceKitSupport extends AnalogConversion {
	val interfaceKitDeviceIdMap: Map[String, List[InputZone]] = Configurator.inputZones.groupBy(_.deviceId)
	val ifks: Map[String, InterfaceKitPhidget] = initInterfaceKit(interfaceKitDeviceIdMap.keySet)

	def inputs() = interfaceKitDeviceIdMap.map(m => m._2).flatten.toList

	def getAnalogInputs() = {
		(for(input <- inputs())
			yield AnalogIO(new java.util.Date, 
				input.position, 
				bitsToVoltage(ifks(input.deviceId).getSensorRawValue(input.position)),
				Some(input.name))
		).toList
	}

	/**
	 * sets the output value for the digital IO based on logical position
	 **/
	def setDigitalOutput(io: DigitalIO) = {
		interfaceKitDeviceIdMap.map(m => {
			inputs.filter(input => input.logicalPosition == io.position).foreach(i => {
				ifks(i.deviceId).setOutputState(io.position, io.value)
			})
		})
		ApiResponse("set output on " + io, 200)
	}

	/**
	 * gets output state for the given logical position
	 **/
	def getDigitalOutputState(logicalPosition: Int): DigitalIO = {
		(for(input <- inputs())
			yield DigitalIO(logicalPosition, ifks(input.deviceId).getOutputState(input.position))
		).head
	}

	/**
	 * initializes all boards
	 **/
	def initInterfaceKit(ids: Set[String]): Map[String, InterfaceKitPhidget] = {
		(for(id <- ids) yield {
			println("initializing id " + id)
			val ifk = new InterfaceKitPhidget
			ifk.open(id.toInt)
			ifk.addAttachListener(new AttachListener() {
				def attached(ae: AttachEvent) = {
					println("InterfaceKit " + id + " attachment of " + ae)
				}
			})
			ifk.addDetachListener(new DetachListener() {
				def detached(ae: DetachEvent) = {
					println("InterfaceKit " + id + " detachment of " + ae)
				}
			})
			ifk.addErrorListener(new ErrorListener() {
				def error(ee: ErrorEvent) = {
					println("InterfaceKit " + id + " " + ee)
				}
			})
			(id, ifk)
		}).toMap
	}

	def disconnectAnalog = {
		ifks.map(m => m._2.close())
	}
}