package models

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.{read, write}

object ModelSerializers {
	val rfcDateParser = new java.text.SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

  implicit val formats = DefaultFormats + 
    new AnalogIOSerializer +
    new AnalogSampleSerializer

  class AnalogIOSerializer extends CustomSerializer[AnalogIO](formats => ({
    case json =>
      implicit val fmts: Formats = formats

      AnalogIO(
        timestamp = rfcDateParser.parse((json \ "timestamp" \ "$date").extract[String]),
        position = (json \ "position").extract[Int],
        name = Some("position_" + (json \ "position").extract[Int]),
        value = (json \ "value").extract[Double]
      )
    }, {
      case x: AnalogIO =>
	      implicit val fmts = formats
	      ("timestamp" -> rfcDateParser.format(x.timestamp)) ~
	      ("position" -> x.position) ~
	      ("value" -> x.value) ~
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