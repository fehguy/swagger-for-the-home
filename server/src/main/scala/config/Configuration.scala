package config

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

  def apply(i:String) = _config.values.getOrElse(i, {
		println("key " + i + " not found")
		""
	})

	def asInt(i:String) = apply(i).toInt

	def hasConfig(i:String) = _config match {
		case c: Configuration => c.values.contains(i)
		case _ => false
	}

	def reload = {
		_config = {
			val file = new File(configFile)
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
				val str = parse(Source.fromFile(file).mkString)
				str.extract[Configuration]
			}
		}
	}
}

case class Configuration(values:Map[String, String])

object Configuration {
	def default = Configuration(Map(
					"dbhost" -> "localhost",
					"dbport" -> "27017",
					"dbuser" -> "dbuser",
					"dbpassword" -> "dbpassword",
					"database" -> "database"))
}