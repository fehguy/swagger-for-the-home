package data

import config._
import models._

import com.wordnik.mongo.connection._
import com.mongodb.BasicDBObjectBuilder

import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import scala.collection.mutable.{ListBuffer, HashMap}
import scala.collection.JavaConverters._

object AnalogDao extends TimestampGenerator {
  val db = MongoDBConnectionManager.getConnection(
    Configurator("dbuser"), Configurator("dbhost"), Configurator.asInt("dbport"), Configurator("database"), Configurator("dbuser"), Configurator("dbpassword"), SchemaType.READ_WRITE)

  def save(io: AnalogIO) = {
  	val dbo = grater[AnalogIO].asDBObject(io)
    dbo.put("_id", "%d_%s".format(io.position, timestampString()))
  	db.getCollection("analog").save(dbo)
  }

  /**
   * just a routine to fix older data
   **/
  def updateData = {
    val cur = db.getCollection("analog").find()

    while(cur.hasNext){
      val dbo = cur.next.asInstanceOf[BasicDBObject]

      // fix old data
      dbo.getOrElse("timestamp", {
        val date = new Date(dbo.get("ts").toString.toLong)
        dbo.put("timestamp", date)
      })
      dbo.remove("ts")
      db.getCollection("analog").save(dbo)
    }
  }

  def findAll(resolution: Long, limit: Int) = {
    val query = BasicDBObjectBuilder.start(Map(
      "timestamp" -> new BasicDBObject("$gte", new Date(System.currentTimeMillis() - (resolution * limit)))
    ).asJava).get
    val cur = db.getCollection("analog").find(query).sort(new BasicDBObject("_id", -1))

    var lastTsPerZone = new HashMap[Int, Long].empty
    val output = new ListBuffer[AnalogIO]
    var done = false
    val countsPerZone = new HashMap[Int, Int].empty

    var recordsInspected = 0
    while(!done && cur.hasNext){
      recordsInspected += 1
      val dbo = cur.next.asInstanceOf[BasicDBObject]
      val event = grater[AnalogIO].asObject(dbo)
      val lastEventTs = lastTsPerZone.getOrElse(event.position, 0L)
      val eventTs = event.timestamp.getTime
      if(Math.abs(eventTs - lastEventTs) > resolution){
        output += grater[AnalogIO].asObject(dbo)
        lastTsPerZone += event.position -> eventTs
      }
    }
    println("inspected " + recordsInspected + " to get " + output.size + " records")
    output.toList.sortWith(_.timestamp.getTime < _.timestamp.getTime)
  }

  def findByChannel(channelId: Int, resolution: Long, limit: Int) = {
    val query = BasicDBObjectBuilder.start(Map(
      "_id" -> Pattern.compile("^" +  channelId)
    ).asJava).get

    val cur = db.getCollection("analog").find(query).sort(new BasicDBObject("_id", -1))

    var lastTs = 0L
    val output = new ListBuffer[AnalogIO]
    var done = false

    while(!done && cur.hasNext){
      val dbo = cur.next.asInstanceOf[BasicDBObject]

      // fix old data
      dbo.getOrElse("timestamp", {
        val date = new Date(dbo.get("ts").toString.toLong)
        dbo.put("timestamp", date)
      })

      val event = grater[AnalogIO].asObject(dbo)
      val ts = event.timestamp.getTime

      if(Math.abs(ts - lastTs) > resolution){
        output += grater[AnalogIO].asObject(dbo)
        lastTs = ts
      }
      if(output.size >= limit) done = true
    }
    output.toList
  }
}

trait TimestampGenerator {
  def timestampString(date: Option[Date] = None): String = {
    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss")
    dateFormatter.format(date match {
      case Some(date) => date
      case None => new Date
    })
  }
}