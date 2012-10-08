package apis

import org.eatbacon.model.AnalogIO
import services._
import com.wordnik.swagger.core.ApiPropertiesReader
import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import JsonDSL._
import org.scalatra.json.{JValueResult, NativeJsonSupport}

import scala.collection.JavaConverters._
import org.json4s.{ DefaultFormats, Formats }

import scala.collection.JavaConverters._

class PhidgetApi (implicit val swagger: Swagger) extends ScalatraServlet 
      with TypedParamSupport 
      with NativeJsonSupport 
      with JValueResult 
      with SwaggerSupport
      with DatatypeSupport {
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

  get("/analog/inputs",
    summary("returns all inputs"),
    nickname("getAnalogInputs"),
    responseClass("LIST[AnalogIO]"),
    endpoint("analog/inputs"),
    notes(""),
    parameters(
      Parameter("test", "text to send",
        paramType = ParamType.Query,
        required = false,
        allowMultiple = false,
        defaultValue = None,
        dataType = DataType("String"))
      
    )) {
      val test = StringDataType(params.contains("test") match {
        case true  => Some(params("test"))
        case false => None
      })
      PhidgetApiService.getAnalogInputs(test)
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
        allowableValues = AllowableValues(0,1,2,3),defaultValue = Some("0"),
        dataType = DataType("Int"))
      
    )) {
      val msg = StringDataType(params.contains("msg") match {
        case true  => params("msg")
        case false => halt(400)
      })
      val lineNumber = IntDataType(params.contains("lineNumber") match {
        case true  => params("lineNumber")
        case false => "0"
      })
      PhidgetApiService.setLcd(msg, lineNumber)
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
        allowableValues = AllowableValues(Range(0,255, 1)),defaultValue = Some("200"),
        dataType = DataType("Int"))
      
    )) {
      val value = IntDataType(params.contains("value") match {
        case true  => params("value")
        case false => "200"
      })
      PhidgetApiService.setContrast(value)
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
        allowableValues = AllowableValues(true,false),defaultValue = Some("true"),
        dataType = DataType("Boolean"))
      
    )) {
      val enabled = BooleanDataType(params.contains("enabled") match {
        case true  => params("enabled")
        case false => "true"
      })
      PhidgetApiService.setBacklight(enabled)
    }

  }
