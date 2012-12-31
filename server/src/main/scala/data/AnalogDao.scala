package data

import config._
import models._

import com.wordnik.mongo.connection._
import com.mongodb.BasicDBObjectBuilder

import java.util.Date
import java.util.regex.Pattern

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit._

import scala.collection.mutable.{ListBuffer, HashMap}
import scala.collection.JavaConverters._
import scala.math._

case class AnalogAggregationPoint(position: Int, average: Double, stdDev: Double, timestamp: Date)

object AnalogDao extends TimestampGenerator {
  val db = MongoDBConnectionManager.getConnection("phidgets", SchemaType.READ_WRITE)

  val keyPoints = List(
    MILLISECONDS.convert(15, TimeUnit.MINUTES),
    MILLISECONDS.convert(1, TimeUnit.HOURS),
    MILLISECONDS.convert(1, TimeUnit.DAYS)
  )

  def save(io: AnalogIO) = {
  	val dbo = grater[AnalogIO].asDBObject(io)
    dbo.put("_id", "%d_%s".format(io.position, timestampString()))
    dbo.remove("name")
  	db.getCollection("analog").save(dbo)
  }

  def computeAverages = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss")
    val r = List(1,4,6,7,10,15)
 
    keyPoints.foreach(keyPoint => {
      r.foreach(pos => {
        var startTime = getAggregate(pos, keyPoint) match {
          case Some(s) => s.timestamp.getTime
          case None => sdf.parse("2012-12-6:01:00:00").getTime
        }
        while(startTime < System.currentTimeMillis) {
          val date = new Date(startTime)
          val data = aggregate(pos, keyPoint, date)
          println(data)
          if(data._3 > 0){
            val agg = AnalogAggregationPoint(data._2, data._3, data._4, date)
            val dbo = grater[AnalogAggregationPoint].asDBObject(agg)
            dbo.put("_id", "%d_%s".format(data._2, timestampString(Some(date))))
            val coll = "analog_" + (keyPoint / (1000 * 60).toInt)
            db.getCollection(coll).save(dbo)
          }
          startTime += keyPoint
        }
      })
    })
  }

  def aggregate(position: Int, resolution: Long, lastTimestamp: Date) = {
    println("aggregating position " + position)
    val query = BasicDBObjectBuilder.start(Map(
      "timestamp" -> new BasicDBObject("$gte", lastTimestamp),
      "position" -> position
    ).asJava).get

    val cur = db.getCollection("analog").find(query).sort(new BasicDBObject("timestamp", 1))
    val endTimestamp = lastTimestamp.getTime + resolution

    var done = false
    var recordsInspected = 0
    val records = new ListBuffer[AnalogIO]
    while(!done && cur.hasNext){
      recordsInspected += 1
      val dbo = cur.next.asInstanceOf[BasicDBObject]
      val event = grater[AnalogIO].asObject(dbo)
      if(event.timestamp.getTime >= endTimestamp) done = true
      else records += event
    }
    val average = records.length match {
      case 0 => 0.0
      case _ => records.map(_.value).sum / records.length
    }
    println("inspected " + recordsInspected + " records for position " + position)
    val stddev = stdDev(records.map(_.value.toDouble).toList, average)
    (lastTimestamp, position, average, stddev, recordsInspected)
  }

  def getAggregate(position: Int, resolution: Long, timestamp: Option[Date] = None) = {
    val query = {
      timestamp match {
        case Some(timestamp) => {
          BasicDBObjectBuilder.start(Map(
            "timestamp" -> new BasicDBObject("$gte", timestamp),
            "position" -> position
          ).asJava).get
        }
        case None => {
          BasicDBObjectBuilder.start(Map(
            "position" -> position
          ).asJava).get
        }
      }
    }

    val coll = "analog_" + (resolution / (1000 * 60).toInt)
    val cur = db.getCollection(coll).find(query).sort(new BasicDBObject("_id", -1)).limit(1)

    if(cur.hasNext) Some(grater[AnalogAggregationPoint].asObject(cur.next))
    else None
  }

  def squaredDifference(value1: Double, value2: Double) = pow(value1 - value2, 2.0)

  def stdDev(list: List[Double], average: Double) = list.isEmpty match {
    case false => {
      val squared = list.foldLeft(0.0)(_ + squaredDifference(_, average))
      sqrt(squared / list.length.toDouble)
    }
    case true => 0.0
  }

  def aggregates: Map[Long, Option[Date]] = {
    val output = new HashMap[Long, Option[Date]]

    keyPoints.foreach(p => {
      val coll = "analog_" + (p / (1000 * 60).toInt)
      val cur = db.getCollection(coll).find().sort(new BasicDBObject("_id", -1)).limit(1)

      if(cur.hasNext) {
        val event = grater[AnalogAggregationPoint].asObject(cur.next)
        output += p -> Some(event.timestamp)
      }
      else
        output += p -> None
    })

    output.toMap
  }

  def findAggregates(resolution: Long, limit: Int) = {
    val coll = "analog_" + (resolution / (1000 * 60).toInt)
    val cur = db.getCollection(coll).find().sort(new BasicDBObject("timestamp", -1))

    val output = new ListBuffer[AnalogAggregationPoint]
    var done = false
    val countsPerZone = new HashMap[Int, Int].empty

    var recordsInspected = 0
    while(!done && cur.hasNext) {
      recordsInspected += 1
      val dbo = cur.next.asInstanceOf[BasicDBObject]
      val event = grater[AnalogAggregationPoint].asObject(dbo)
      val eventTs = event.timestamp.getTime
      val c = countsPerZone.getOrElse(event.position, 0) + 1
      if(c < limit) {
        countsPerZone += event.position -> c
        output += event
      }
      if(countsPerZone.map(m => m._2).min > limit) {
        println("done with limit " + countsPerZone)
        done = true
      }
    }
    output.toList.sortWith(_.timestamp.getTime < _.timestamp.getTime)
  }

  def findAll(resolution: Long, limit: Int) = {
    val query = BasicDBObjectBuilder.start(Map(
      "timestamp" -> new BasicDBObject("$gte", new Date(System.currentTimeMillis() - (resolution * limit)))
    ).asJava).get

    val coll = "analog"
    val cur = db.getCollection(coll).find(query).sort(new BasicDBObject("_id", -1))

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