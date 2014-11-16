package config

import models.InputZone

import org.json4s._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._

import scala.io.Source

object Configurator {
	implicit val formats = DefaultFormats

	var _config: Configuration = null

	reload

  def apply(i:String) = _config.values.getOrElse(i, {
		println("key " + i + " not found")
		""
	})

	def asInt(i:String) = apply(i).toInt

	def hasConfig(i:String) = _config match {
		case c: Configuration => c.values.contains(i)
		case _ => false
	}

	def reload = _config = Configuration.default
}

case class Configuration(
	values:Map[String, String],
	var inputZones: List[InputZone] = List.empty
)

object Configuration {
	def default = Configuration(Map(
    "dbhost" -> "dbh00.mongolab.com",
    "dbport" -> "27007",
    "dbuser" -> "phidgets",
    "dbpassword" -> "phidgetz",
    "database" -> "webster",
    "mongolabApiKey" -> "4d294b96ac444c5e2b94652f"
	))
}