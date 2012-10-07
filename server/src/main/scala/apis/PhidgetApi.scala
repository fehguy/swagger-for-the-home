package apis

import com.wordnik.swagger.core.ApiPropertiesReader
import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.scalatra.json._

import scala.collection.JavaConverters._
import org.json4s.{ DefaultFormats, Formats }

import scala.collection.JavaConverters._

class PhidgetApi (implicit val swagger: Swagger) extends ScalatraServlet with TypedParamSupport with JacksonJsonSupport with JValueResult with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "PhidgetApi"
  override protected val applicationName: Option[String] = Some("phidget")

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

  get("/lcd",
    summary("Updates the LCD"),
    nickname("setLcd"),
    responseClass("void"),
    endpoint("lcd"),
    notes(""),
    parameters(
      Parameter("msg", "text to send",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = None,
        dataType = DataType("String"))
      ,
      Parameter("lineNumber", "Line to update",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = Some("1"),
        dataType = DataType("Int"))
      
      )) {
    PhidgetService.toLcd(params.getOrElse("lineNumber", halt(400)).toInt, params("msg"))
  }

  get("/lcd/contrast",
    summary("Updates the LCD"),
    nickname("setContrast"),
    responseClass("void"),
    endpoint("lcd/contrast"),
    notes(""),
    parameters(
      Parameter("value", "contrast to set to",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = Some("200"),
        dataType = DataType("Int"))
      
      )) {
    PhidgetService.setContrast(params.getOrElse("value", halt(400)).toInt)
  }

  get("/lcd/backlight",
    summary("Updates the LCD backlight"),
    nickname("setBacklight"),
    responseClass("void"),
    endpoint("lcd/backlight"),
    notes(""),
    parameters(
      Parameter("enabled", "turn on or off light",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = Some("true"),
        dataType = DataType("String"))
      
      )) {
      PhidgetService.setBacklight(params.getOrElse("enabled", halt(400)).toBoolean)
    }
  }
