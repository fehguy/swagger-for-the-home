package data

import config._
import model._

import com.wordnik.mongo.connection._
import com.mongodb.BasicDBObjectBuilder

import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit._

import scala.collection.mutable.{ListBuffer, HashMap}
import scala.collection.JavaConverters._
import scala.math._

object DigitalDao extends TimestampGenerator {
  val db = MongoDBConnectionManager.getConnection("phidgets", SchemaType.READ_WRITE)

  val keyPoints = List(
    MILLISECONDS.convert(15, TimeUnit.MINUTES),
    MILLISECONDS.convert(1, TimeUnit.HOURS),
    MILLISECONDS.convert(1, TimeUnit.DAYS)
  )

  def save(io: DigitalIO) = {
  	// get last status for this channel
  	findLastByChannel(io.position) match {
  		case Some(last) => {
  			if (last.`value` == io.`value`) 
  				delete(last)
	  	}
  		case None => 
  	}
    val date = new Date
    val ioCopy = io.copy(timestamp = Some(date))
  	val dbo = grater[DigitalIO].asDBObject(ioCopy)
    dbo.put("_id", "%d_%s".format(io.position, timestampString(Some(date))))

  	db.getCollection("digital_out").save(dbo)
  }

  def findLastByChannel(position: Int): Option[DigitalIO] = {
    val query = BasicDBObjectBuilder.start(Map(
      "position" -> position
    ).asJava).get

    val cur = db.getCollection("digital_out").find(query).sort(new BasicDBObject("_id", -1))

    cur.hasNext match {
    	case true => Some(grater[DigitalIO].asObject(cur.next))
	    case false => None
	  }
  }

  def delete(io: DigitalIO) = {
    val query = BasicDBObjectBuilder.start(Map(
      "_id" -> "%d_%s".format(io.position, timestampString(io.timestamp))
    ).asJava).get

    db.getCollection("digital_out").remove(query)
  }
}
