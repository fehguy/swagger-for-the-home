package apis

import org.eatbacon.model.Zone
import com.wordnik.swagger.core.ApiPropertiesReader
import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import JsonDSL._
import org.scalatra.json.{JValueResult, NativeJsonSupport}

import scala.collection.JavaConverters._
import org.json4s.{ DefaultFormats, Formats }

import scala.collection.JavaConverters._

class HydronicsApi (implicit val swagger: Swagger) extends ScalatraServlet with TypedParamSupport with NativeJsonSupport with JValueResult with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "HydronicsApi"
  override protected val applicationName: Option[String] = Some("hydronics")

  def swaggerToModel(cls: Class[_]) = {
    val docObj = ApiPropertiesReader.read(cls)
    val name = docObj.getName
    val fields = for (field <- docObj.getFields.asScala.filter(d => d.paramType != null))
      yield (field.name -> ModelField(field.name, field.notes, DataType(field.paramType)))

    Model(name, name, fields.toMap)
  }

  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  get("/zones",
    summary("Get all zones"),
    nickname("getZones"),
    responseClass("List[Zone]"),
    endpoint("zones"),
    notes("Returns an array of zones"),
    parameters(
      )) {
  }

  }
