import services._
import apis._
import config._

import org.eatbacon.app._
import com.wordnik.mongo.connection._

import javax.servlet.ServletContext
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  implicit val swagger = new SwaggerApp

  override def init(context: ServletContext) {
    try {
      context mount (new HydronicsApi, "/hydronics/*")
      context mount (new PhidgetApi, "/phidget/*")
      context mount (new ResourcesApp, "/*")

      MongoDBConnectionManager.getConnection(
        "phidgets", 
        Configurator("dbhost"), 
        Configurator.asInt("dbport"), 
        Configurator("database"), 
        Configurator("dbuser"), 
        Configurator("dbpassword"), 
        SchemaType.READ_WRITE)

      HydronicSupport.startUpdate
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
