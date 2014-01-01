package config

import model._

import org.json4s._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._

import java.io.File

import scala.io.Source

object Configurator {
	implicit val formats = DefaultFormats

	val configFile = "config.json"
	var _config: Configuration = null

	reload

	def config = _config

  def apply(i:String) = _config.values.getOrElse(i, {
		println("key " + i + " not found")
		""
	})

	def asInt(i:String) = apply(i).toInt

	def hasConfig(i:String) = _config match {
		case c: Configuration => c.values.contains(i)
		case _ => false
	}

	def load(input: String): Configuration = {
		val str = parse(input)
		_config = str.extract[Configuration]
		_config
	}

	def inputZones = _config.inputZones

	def outputZones = _config.outputZones

	def reload = {
		_config = {
			val file = new File(configFile)
			println(file.getAbsolutePath)
			if(!file.exists){
				val pw = new java.io.PrintWriter(file)
				try{
					println("wrote default config! " + write(Configuration.default))
					pw.write(write(Configuration.default))
				}
				finally{
					pw.close
				}
				Configuration.default
			}
			else {
				load(Source.fromFile(file).mkString)
			}
		}
	}
}

case class Configuration(
	values:Map[String, String],
	inputZones: List[InputZone] = List.empty,
	outputZones: List[OutputZone] = List.empty)

object Configuration {
	def default = Configuration(Map(
					"dbhost" -> "localhost",
					"dbport" -> "27017",
					"dbuser" -> "dbuser",
					"dbpassword" -> "dbpassword",
					"database" -> "database"))
}