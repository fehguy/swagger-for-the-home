package services

object HydronicSupport {
	def startUpdate = {
		val thd = new UpdateThread
		thd.start
	}
}

trait HydronicSupport {
	def getZones() = {}
}

class UpdateThread extends Thread {
	override def run() = {
		while(true) {
			Thread.sleep(1000)
			val analog = PhidgetApiService.getAnalogInputs()(7)
			val msg = "Temp is %.2f F".format(analog.value)
			PhidgetApiService.setLcd(msg, 0)
		}
	}
}