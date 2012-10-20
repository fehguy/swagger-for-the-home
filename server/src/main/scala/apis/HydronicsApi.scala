package apis

import models.Zone
import models.AnalogIO
import services._
import com.wordnik.util.perf._
import com.wordnik.swagger.core.ApiPropertiesReader

import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{JValueResult, NativeJsonSupport}

import scala.collection.JavaConverters._

class HydronicsApi (implicit val swagger: Swagger) extends ScalatraServlet 
      with TypedParamSupport 
      with NativeJsonSupport 
      with JValueResult 
      with SwaggerSupport
      with SwaggerDatatypeConversionSupport {
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
      Parameter(name = "limit", 
        description = "max values to fetch",
        paramType = ParamType.Query,
        required = false,
        allowMultiple = false,
        defaultValue = None,
        dataType = DataType("Int"))
      ,Parameter(name = "resolution", 
        description = "fetch resolution",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = Some("hour"),
        dataType = DataType("String"))
      )) {
    val limit = IntDataType(params.contains("limit") match {
      case true  => Some(params("limit"))
      case false => None
      })
    val resolution = StringDataType(params.contains("resolution") match {
      case true  => params("resolution")
      case false => "hour"
      })
    Profile("/zones (get)", HydronicsApiService.getZones(limit, resolution), true)
  }

  get("/zone/:zoneId",
    summary("Gets a specific zone"),
    nickname("getZone"),
    responseClass("List[AnalogIO]"),
    endpoint("zone/{zoneId}"),
    notes("Returns an array of zones"),
    parameters(
      Parameter(name = "zoneId", 
        description = "zone to fetch for",
        dataType = DataType.String,
        defaultValue = None,
        paramType = ParamType.Path)
      ,Parameter(name = "limit", 
        description = "max values to fetch",
        paramType = ParamType.Query,
        required = false,
        allowMultiple = false,
        defaultValue = None,
        dataType = DataType("Int"))
      ,Parameter(name = "resolution", 
        description = "fetch resolution",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = Some("hour"),
        dataType = DataType("String"))
      )) {
    val zoneId = IntDataType(params.contains("zoneId") match {
      case true  => params("zoneId")
      case false => halt(400)
      })
    val limit = IntDataType(params.contains("limit") match {
      case true  => Some(params("limit"))
      case false => None
      })
    val resolution = StringDataType(params.contains("resolution") match {
      case true  => params("resolution")
      case false => "hour"
      })
    Profile("/zone/:zoneId (get)", HydronicsApiService.getZone(zoneId, limit, resolution), true)
  }
}
