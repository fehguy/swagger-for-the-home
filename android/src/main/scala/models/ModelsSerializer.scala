package models

import config.Configuration

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.{read, write}

object ModelSerializers {
	val rfcDateParser = new java.text.SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

  implicit val formats = DefaultFormats + 
    new AnalogIOSerializer +
    new AnalogSampleSerializer +
    new ConfigurationSerializer +
    new InputZoneSerializer

  class ConfigurationSerializer extends CustomSerializer[Configuration](formats => ({
    case json =>
      implicit val fmts: Formats = formats

      Configuration(
        Map(),
        (json \ "inputZones").extract[List[InputZone]]
      )
    }, {
      case x: Configuration =>
        implicit val fmts = formats
        ("inputZones" -> {
          x.inputZones match {
            case e: List[InputZone] if (e.size > 0) => Extraction.decompose(e)
            case _ => JNothing
          }
        })
    }
  ))

  class InputZoneSerializer extends CustomSerializer[InputZone](formats => ({
    case json =>
      implicit val fmts: Formats = formats

      InputZone(
        inputDeviceId = (json \ "inputDeviceId").extract[String],
        position = (json \ "position").extract[Int],
        logicalPosition = (json \ "logicalPosition").extract[Int],
        name = (json \ "name").extract[String]
      )
    }, {
      case x: InputZone =>
        implicit val fmts = formats
        ("inputDeviceId" -> x.inputDeviceId) ~
        ("position" -> x.position) ~
        ("logicalPosition" -> x.logicalPosition) ~
        ("name" -> x.name)
    }
  ))

  class AnalogIOSerializer extends CustomSerializer[AnalogIO](formats => ({
    case json =>
      implicit val fmts: Formats = formats

      AnalogIO(
        position = (json \ "position").extract[Int],
        value = (json \ "value").extract[Double],
        timestamp = rfcDateParser.parse((json \ "timestamp" \ "$date").extract[String]),
        name = Some("position_" + (json \ "position").extract[Int])
      )
    }, {
      case x: AnalogIO =>
	      implicit val fmts = formats
        ("position" -> x.position) ~
        ("value" -> x.value) ~
	      ("timestamp" -> rfcDateParser.format(x.timestamp)) ~
	      ("name" -> x.name)
    }
  ))

  class AnalogSampleSerializer extends CustomSerializer[AnalogSample](formats => ({
    case json =>
      implicit val fmts: Formats = formats

      AnalogSample(
        position = (json \ "position").extract[Int],
        average = (json \ "average").extract[Double],
        stdDev = (json \ "stdDev").extract[Double],
        timestamp = rfcDateParser.parse((json \ "timestamp" \ "$date").extract[String]),
        name = Some("position_" + (json \ "position").extract[Int])
      )
    }, {
      case x: AnalogSample =>
	      implicit val fmts = formats
	      ("position" -> x.position) ~
	      ("average" -> x.average) ~
	      ("stdDev" -> x.stdDev) ~
	      ("timestamp" -> rfcDateParser.format(x.timestamp)) ~
	      ("name" -> x.name)
    }
  ))
}