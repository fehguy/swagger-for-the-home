import api._

import config._
import service.{ ScheduleSupport, ZwaveApiService }

import com.wordnik.mongo.connection._

import akka.actor.ActorSystem
import com.wordnik.swagger.app.{ ResourcesApp, SwaggerApp }
import javax.servlet.ServletContext
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  implicit val swagger = new SwaggerApp

  override def init(context: ServletContext) {
    implicit val system = ActorSystem("mySystem")
    try {
      context mount (new PhidgetApi, "/phidget/*")
      context mount (new EnvironmentApi, "/environment/*")
      context mount (new ZwaveApi, "/zwave/*")
      context mount (new ResourcesApp, "/api-docs/*")

      MongoDBConnectionManager.getConnection("phidgets",
        Configurator("dbhost"),
        Configurator.asInt("dbport"),
        Configurator("database"),
        Configurator("dbuser"),
        Configurator("dbpassword"),
        SchemaType.READ_WRITE)

      ScheduleSupport.startUpdate
      ZwaveApiService.startUpdate
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
