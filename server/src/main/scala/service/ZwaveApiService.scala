package service

import model._

import akka.actor._
import akka.actor.Actor
import akka.actor.Props

import org.json4s._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._

import java.util.concurrent.TimeUnit
import java.net.{ HttpURLConnection, URL }
import java.io.{ DataOutputStream, BufferedReader, InputStreamReader }

import scala.collection.mutable.HashMap
import scala.concurrent.duration._

object ZwaveApiService {
  val switches = Set(2, 5, 6, 9)
  val dimmers = Set(3, 4, 8, 10, 11)

  val switchTimers: HashMap[Int, Long] = new HashMap[Int, Long]
  val dimmerTimers: HashMap[Int, Long] = new HashMap[Int, Long]

  val system = ActorSystem("ZwaveScheduler")
  import system.dispatcher

  var zwaveSchedulerCancellable: Option[Cancellable] = None

  def startUpdate = {
    zwaveSchedulerCancellable = Some(system.scheduler.schedule(15 seconds, Duration.create(30, TimeUnit.SECONDS), new Runnable {
      def run() = {
        switchTimers.map(m => {
          if (m._2 < System.currentTimeMillis) {
            setSwitchValue(m._1, 0)
            switchTimers.remove(m._1)
            println("shut off switch for " + m._1)
          } else
            println("not time for switch " + m._1)
        })
        dimmerTimers.map(m => {
          if (m._2 < System.currentTimeMillis) {
            setDimmerValue(m._1, 0)
            dimmerTimers.remove(m._1)
            println("shut off dimmer for " + m._1)
          } else
            println("not time for dimmer " + m._1)
        })
      }
    }))
  }

  def getDevices() = {

  }

  def getDeviceState(deviceId: Int) = {
    val url = new URL("http://10.0.0.90:8083/ZWaveAPI/Data/100")
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.setInstanceFollowRedirects(false)
    connection.setRequestMethod("POST")
    connection.setRequestProperty("Content-Length", "0")
    connection.setUseCaches(false)

    val wr = new DataOutputStream(connection.getOutputStream())
    wr.flush()

    val reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
    var line: String = ""
    val buffer = new StringBuffer
    while (line != null) {
      line = reader.readLine()
      if (line != null)
        buffer.append(line)
    }

    val isSwitch = switches.contains(deviceId)
    val isDimmer = dimmers.contains(deviceId)
    val state = {
      if (isSwitch) {
        val json = parse(buffer.toString)
        (json \ "devices" \ deviceId.toString \ "instances" \ "0" \ "commandClasses" \ "37" \ "data" \ "level" \ "value") match {
          case JInt(e) => e.toInt
          case _ => 0
        }
      } else if (isDimmer) {
        val json = parse(buffer.toString)
        (json \ "devices" \ deviceId.toString \ "instances" \ "0" \ "commandClasses" \ "38" \ "data" \ "level" \ "value") match {
          case JInt(e) => e.toInt
          case _ => 0
        }
      } else {
        0
      }
    }
    DeviceState(deviceId, state)
  }

  def setDimmerValue(deviceId: Int, value: Int) = {
    val url = new URL("http://10.0.0.90:8083/ZWaveAPI/Run/devices[%d].instances[0].commandClasses[0x26].Set(%d)".format(deviceId, value))
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.setInstanceFollowRedirects(false)
    connection.setRequestMethod("POST")
    connection.setRequestProperty("Content-Length", "0")
    connection.setUseCaches(false)

    val wr = new DataOutputStream(connection.getOutputStream())
    wr.flush()

    val reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
    var line: String = ""
    val buffer = new StringBuffer
    while (line != null) {
      line = reader.readLine()
      if (line != null)
        buffer.append(line)
    }
    reader.close()
    wr.close()
    connection.disconnect()
    val str = buffer.toString
    dimmerTimers.remove(deviceId)
  }

  def setSwitchValue(deviceId: Int, value: Int) = {
    val url = new URL("http://10.0.0.90:8083/ZWaveAPI/Run/devices[%d].instances[0].commandClasses[0x25].Set(%d)".format(deviceId, value))
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.setInstanceFollowRedirects(false)
    connection.setRequestMethod("POST")
    connection.setRequestProperty("Content-Length", "0")
    connection.setUseCaches(false)

    val wr = new DataOutputStream(connection.getOutputStream())
    wr.flush()

    val reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
    var line: String = ""
    val buffer = new StringBuffer
    while (line != null) {
      line = reader.readLine()
      if (line != null)
        buffer.append(line)
    }
    reader.close()
    wr.close()
    connection.disconnect()
    val str = buffer.toString
    switchTimers.remove(deviceId)
  }

  def switchOnWithTimer(deviceId: Int, timer: Int) = {
    setSwitchValue(deviceId, 255)
    switchTimers += deviceId -> (System.currentTimeMillis + (timer * 60000))
  }

  def dimmerOnWithTimer(deviceId: Int, timer: Int) = {
    setDimmerValue(deviceId, 255)
    dimmerTimers += deviceId -> (System.currentTimeMillis + (timer * 60000))
  }
}