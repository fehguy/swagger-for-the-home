package api

import model._

import service._

import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }

import scala.collection.JavaConverters._

class EnvironmentApi(implicit val swagger: Swagger) extends ScalatraServlet
    with JacksonJsonSupport
    with SwaggerSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "EnvironmentApi"
  override protected val applicationName: Option[String] = Some("environment")

  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  error {
    case e => {
      Map("message" -> e.getMessage, "code" -> "500")
    }
  }

  val getForecastOperation = (apiOperation[List[TemperatureForecast]]("getForecast")
    summary "Gets the forecast"
    parameters (
      pathParam[Int]("days").description("Number of days to forecast"))
  )

  get("/forecast/:days", operation(getForecastOperation)) {
    val days: Int = params.getAs[Int]("days").getOrElse(halt(400))

    EnvironmentApiService.getForecast(days)
  }
}
