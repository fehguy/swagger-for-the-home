package data

import config._
import models.ModelSerializers
import com.wordnik.util.perf.Profile
import android.util.Log

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.read

import scala.io._

object ConfigurationDao {
  implicit val fmts = ModelSerializers.formats

	def reload() = {
		val data = Profile("loadConfig", {
      val srcUrl = "https://api.mongolab.com/api/1/databases/%s/collections/config?apiKey=%s".format(
      	Configurator("database"), 
      	Configurator("mongolabApiKey")
      )

      Log.d("ConfigurationDao", "calling " + srcUrl)
      try {
        val str = Source.fromURL(srcUrl).mkString
        val json = parse(str)
        Configurator._config.inputZones = (json.extract[Configuration]).inputZones
      }
      catch {
        case e: Exception => {
          Log.d(APP.name, "failed " + e.getMessage)
          None
        }
      }
    })
  }
}