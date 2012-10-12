package data

import config._
import models._

import com.wordnik.mongo.connection._

import java.text.SimpleDateFormat

import com.novus.salat._
import com.novus.salat.global._

object Data {
  val db = MongoDBConnectionManager.getConnection(
    Configurator("dbuser"), Configurator("dbhost"), Configurator.asInt("dbport"), Configurator("database"), Configurator("dbuser"), Configurator("dbpassword"), SchemaType.READ_WRITE)

  def save(io: AnalogIO) = {
  	val dbo = grater[AnalogIO].asDBObject(io)

  	dbo.put("_id", io.position + "-" + getTimestampString)
  	db.getCollection("analog").save(dbo)
  }

  def getTimestampString: String = {
    val dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss")
    dateFormatter.format(new java.util.Date)
  }
}