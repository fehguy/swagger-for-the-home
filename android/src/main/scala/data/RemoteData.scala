package data

import config._
import models._
import com.wordnik.util.perf.Profile

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.read

import java.io._
import java.net._
import java.util.Date

import scala.io._
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

import android.widget._
import android.util.Log

object RemoteData {
  val r = List(1,4,6,7,10,15)

  implicit val fmts = ModelSerializers.formats
  val values = new java.util.ArrayList[String]()
  var lastValues: List[AnalogIO] = List.empty
  val sdf = new java.text.SimpleDateFormat("MM/dd HH:mm:ss")
  val rfcDateParser = new java.text.SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

  def valuesForPosition(position: Int, limit: Int = 50): List[AnalogSample] = {
    val data = Profile("getSingleValue", {
      val srcUrl = "https://api.mongolab.com/api/1/databases/%s/collections/analog_15?apiKey=%s&l=%d&s={_id:-1}&q={\"_id\":{\"$regex\":\"^%d_\"}}".format(
        Configurator("database"), 
        Configurator("mongolabApiKey"), 
        limit, 
        position)

      Log.d(APP.name, "calling " + srcUrl)
      try {
        val str = Source.fromURL(srcUrl).mkString
        val json = parse(str)
        Some(json.extract[List[AnalogSample]])
      }
      catch {
        case e: Exception => {
          Log.d(APP.name, "failed " + e.getMessage)
          e.printStackTrace
          None
        }
      }
    })
    data.flatten.toList
  }

  val listeners = new ListBuffer[ArrayAdapter[_]]

  def apply() = {
    val data = (for(i <- r) yield {
      Profile("getSingleValue", {
        val srcUrl = "https://api.mongolab.com/api/1/databases/%s/collections/analog?apiKey=%s&l=1&s={_id:-1}&q={\"_id\":{\"$regex\":\"^%d_\"}}".format(
          Configurator("database"), 
          Configurator("mongolabApiKey"), 
          i)
        Log.d(APP.name, "calling " + srcUrl)
        try {
          val str = Source.fromURL(srcUrl).mkString
          val json = parse(str)
          Log.d(APP.name, "got " + json.toString)
          Some(json.extract[AnalogIO])
        }
        catch {
          case e: Exception => {
            Log.d(APP.name, "failed " + e.getMessage)
            None
          }
        }
      })
    }).flatten.toList

    values.clear
    data.foreach(i => {
      lastValues match {
        case oldValues: List[AnalogIO] if(oldValues.size > 0) => {
          val old = oldValues.filter(_.position == i.position).head
          val diff = i.value - old.value
          values.add("%s: %.2f (%.2f)".format(friendlyName(i.position), i.value, diff))
        }
        case _ => {
          values.add("%s: %.2f".format(friendlyName(i.position), i.value))
        }
      }
    })
    val oldestUpdate = data.minBy{_.timestamp}
    val age = (new java.util.Date().getTime - oldestUpdate.timestamp.getTime) / 1000.0 + 28800

    values.add(0, "Updated %s (%d seconds old)".format(sdf.format(new java.util.Date), age.toInt))
    values.add("See all")
    listeners.foreach(_.notifyDataSetChanged)
    lastValues = data
  }

  def friendlyName(pos: Int) = {
    val map = Configurator._config.inputZones.map(m => (m.logicalPosition, m.name)).toMap
    map.getOrElse(pos, "Unknown")
  }
}