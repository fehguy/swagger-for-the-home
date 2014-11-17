import org.json4s._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._

import scala.io._

object Test {
  implicit val formats = DefaultFormats

  def main(args: Array[String]) = {
    val jsonString = Source.fromFile("../fun.json").mkString

    val json = parse(jsonString)
    println("3 " + (json \ "devices" \ "3" \ "instances" \ "0" \ "commandClasses" \ "38" \ "data" \ "level" \ "value"))
    println("4 " + (json \ "devices" \ "4" \ "instances" \ "0" \ "commandClasses" \ "38" \ "data" \ "level" \ "value"))
    println("5 " + (json \ "devices" \ "5" \ "instances" \ "0" \ "data" \ "dynamic" \ "value"))
    println("6 " + (json \ "devices" \ "6" \ "instances" \ "0" \ "commandClasses" \ "37" \ "data" \ "level" \ "value"))
    println("7 " + (json \ "devices" \ "7" \ "instances" \ "0" \ "commandClasses" \ "38" \ "data" \ "level" \ "value"))
    println("8 " + (json \ "devices" \ "8" \ "instances" \ "0" \ "commandClasses" \ "38" \ "data" \ "level" \ "value"))

  }
}
