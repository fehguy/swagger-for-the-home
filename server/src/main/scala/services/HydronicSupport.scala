package services

import data._

import akka.actor._
import akka.util.Duration
import akka.util.duration._

import java.util.concurrent.TimeUnit

object HydronicSupport {
	var saveCancellable: Option[Cancellable] = None
	var lcdCancellable: Option[Cancellable] = None
  val system = ActorSystem("HydronicScheduler")


	def startUpdate = {
		saveCancellable = Some(system.scheduler.schedule(30 seconds, Duration.create(30, TimeUnit.SECONDS), new Runnable {
			def run() {
				val analog = PhidgetApiService.getAnalogInputs()(7)
				Data.save(analog)
			}
		}))

		lcdCancellable = Some(system.scheduler.schedule(5 second, Duration.create(.5, TimeUnit.SECONDS), new Runnable {
			def run() {
				val analog = PhidgetApiService.getAnalogInputs()(7)
				val msg = "Temp is %.1f F" format(analog.value)
				PhidgetApiService.setLcd(msg, 0)
			}
		}))
	}
}

trait HydronicSupport {
	def getZones() = {}
}
