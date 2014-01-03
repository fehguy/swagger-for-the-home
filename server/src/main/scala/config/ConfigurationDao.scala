package config

import com.wordnik.mongo.connection._

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

object ConfigurationDao {
  val collection = "config"
  val db = MongoDBConnectionManager.getConnection("phidgets", SchemaType.READ_WRITE)

  def save(config: Configuration) = {
    val dbo = grater[Configuration].asDBObject(config)
    dbo.remove("dbpassword")
    dbo.put("_id", "1")
    db.getCollection(collection).save(dbo)
  }

  def read = {
    val cur = db.getCollection(collection).find()
    val dbo = cur.next.asInstanceOf[BasicDBObject]
    grater[Configuration].asObject(dbo)
  }
}