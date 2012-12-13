package data

import config.APP
import com.wordnik.util.perf.Profile

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.json4s.native.Serialization.read

import java.io._
import java.net._

import scala.io._
import scala.collection.JavaConverters._

import android.util.Log

object RemoteData {
  implicit val fmts = DefaultFormats
  val values = new java.util.ArrayList[String]()
  val sdf = new java.text.SimpleDateFormat("MM/dd HH:mm:ss")

  def apply() = {
    var data: List[String] = List.empty
    val r = List(1,4,6,7,10,15)
    val l = Profile("getData", {
      data = (for(i <- r) 
        yield {
          Profile("getSingleValue", {
            val srcUrl = "https://api.mongolab.com/api/1/databases/XXXXXXXXXXXXXXXXX&l=1&s={_id:-1}&q={\"_id\":{\"$regex\":\"^%d_\"}}".format(i)
            val url = new URL(srcUrl)
            val urlConnection = url.openConnection().asInstanceOf[HttpURLConnection]
            Log.d(APP.name, "calling " + srcUrl)
            try {
              val in = new BufferedInputStream(urlConnection.getInputStream())
              val str = Source.fromInputStream(in).mkString
              val json = parse(str)
              val id = (json \ "position").extract[Int]
              val value = (json \ "value").extract[Double]
              Log.d(APP.name, "got " + json.toString)

              "%d: %.2f".format(id, value)
            }
            catch {
              case e: Exception => {
                Log.d(APP.name, "failed " + e.getMessage)
                "%d read failed %s".format(i, srcUrl)
              }
            }
            finally {
              urlConnection.disconnect()
            }
          })
        }
      ).toList
    })
    val counter = Profile.counters("getSingleValue")
    val oldValues = values.asScala.filter(_.indexOf("Updated") == -1).map(_.split(": ").toList).map(m => (m(0),m(1))).toMap

    val newValues = data.map(_.split(": ").toList).map(m => (m(0),m(1))).toMap

    values.clear
    values.add("Updated %s".format(sdf.format(new java.util.Date)))
    newValues.map(m => {
      oldValues.contains(m._1) match {
        case true => {
          val oldValue = oldValues(m._1) match {
            case e:String if (e.indexOf(" ") > 0) => e.split(" ")(0)
            case e:String => e
          }
          val diff = m._2.toDouble - oldValue.toDouble
          val sym = diff match {
            case i: Double if (i < 0) => "-"
            case _ => ""
          }
          values.add("%d: %.2f (%s%.2f)".format(m._1.toInt, m._2.toDouble, sym, diff))
        }
        case _ => values.add("%d: %.2f".format(m._1.toInt, m._2.toDouble))
      }
    })
  }
}