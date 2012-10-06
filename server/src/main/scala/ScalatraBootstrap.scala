import apis._
import org.eatbacon.app._
import javax.servlet.ServletContext
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {
  implicit val swagger = new SwaggerApp

  override def init(context: ServletContext) {
    try {
      context mount (new HydronicsApi, "/hydronics/*")
      context mount (new ResourcesApp, "/*")
    } catch {
      case e: Throwable => e.printStackTrace()
    }
  }
}
