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
				val inputs = PhidgetApiService.getAnalogInputs()
				inputs.foreach(analog => {
					if(analog.value > 0.1)
						Data.save(analog)
				})
			}
		}))

		lcdCancellable = Some(system.scheduler.schedule(5 second, Duration.create(20, TimeUnit.SECONDS), new Runnable {
			def run() {
				val inputs = PhidgetApiService.getAnalogInputs()
				inputs.foreach(analog => {
					val msg = "Channel %d is %.1f F" format(analog.position, analog.value)
					PhidgetApiService.setLcd(msg, 0)
					Thread.sleep(2000)
				})
			}
		}))
	}
}

trait HydronicSupport {
	def getZones() = {}
}
