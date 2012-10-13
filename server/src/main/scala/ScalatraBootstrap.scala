import apis._
import services._
import org.eatbacon.app._
import javax.servlet.ServletContext
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  implicit val swagger = new SwaggerApp

  override def init(context: ServletContext) {
    try {
      context mount (new HydronicsApi, "/hydronics/*")
      context mount (new PhidgetApi, "/phidget/*")
      context mount (new ResourcesApp, "/*")

      HydronicSupport.startUpdate
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
