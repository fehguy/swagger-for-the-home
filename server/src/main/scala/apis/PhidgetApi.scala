package apis

import models.AnalogIO
import models.DigitalIO
import services._
import com.wordnik.util.perf._
import com.wordnik.swagger.core.ApiPropertiesReader

import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{JValueResult, NativeJsonSupport}

import scala.collection.JavaConverters._

class PhidgetApi (implicit val swagger: Swagger) extends ScalatraServlet 
      with TypedParamSupport 
      with NativeJsonSupport 
      with JValueResult 
      with SwaggerSupport
      with SwaggerDatatypeConversionSupport {
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
    notes("Gives a list of all analog IO values"),
    parameters(
      )) {
    Profile("/analog/inputs (get)", PhidgetApiService.getAnalogInputs(), true)
  }

  post("/relay/output",
    summary("sets an output"),
    nickname("setRelayOutput"),
    responseClass("DigitalIO"),
    endpoint("relay/output"),
    notes("Sets the specified IO"),
    parameters(
      Parameter(name = "body",
        description = "digital IO value to set",
        dataType = DataType("DigitalIO"),
        defaultValue = None,
        paramType = ParamType.Body)
      )) {
    val body = (parsedBody.extract[DigitalIO] match {
      case e: DigitalIO => e
      case _ => halt(400)
      })
    Profile("/relay/output (post)", PhidgetApiService.setRelayOutput(body), true)
  }

  get("/relay/output/:position",
    summary("gets an output state"),
    nickname("getRelayOutput"),
    responseClass("DigitalIO"),
    endpoint("relay/output/{position}"),
    notes("Gets the specified IO"),
    parameters(
      Parameter(name = "position", 
        description = "positon to fetch",
        dataType = DataType.String,
        allowableValues = AllowableValues(0,1,2,3,4,5,6,7),defaultValue = Some("0"),
        paramType = ParamType.Path)
      )) {
    val position = IntDataType(params.contains("position") match {
      case true  => params("position")
      case false => "0"
      })
    Profile("/relay/output/:position (get)", PhidgetApiService.getRelayOutput(position), true)
  }

  post("/digital/output",
    summary("sets an output"),
    nickname("setDigitalOutput"),
    responseClass("DigitalIO"),
    endpoint("digital/output"),
    notes("Sets the specified IO"),
    parameters(
      Parameter(name = "body",
        description = "digital IO value to set",
        dataType = DataType("DigitalIO"),
        defaultValue = None,
        paramType = ParamType.Body)
      )) {
    val body = (parsedBody.extract[DigitalIO] match {
      case e: DigitalIO => e
      case _ => halt(400)
      })
    Profile("/digital/output (post)", PhidgetApiService.setDigitalOutput(body), true)
  }

  get("/digital/output/:position",
    summary("gets an output state"),
    nickname("getDigitalOutputState"),
    responseClass("DigitalIO"),
    endpoint("digital/output/{position}"),
    notes("Gets the specified IO"),
    parameters(
      Parameter(name = "position", 
        description = "positon to fetch",
        dataType = DataType.String,
        allowableValues = AllowableValues(0,1,2,3,4,5,6,7),defaultValue = Some("0"),
        paramType = ParamType.Path)
      )) {
    val position = IntDataType(params.contains("position") match {
      case true  => params("position")
      case false => "0"
      })
    Profile("/digital/output/:position (get)", PhidgetApiService.getDigitalOutputState(position), true)
  }

  get("/lcd",
    summary("Updates the LCD"),
    nickname("setLcd"),
    responseClass("void"),
    endpoint("lcd"),
    notes("Pass a line number and string to update on the LCD"),
    parameters(
      Parameter(name = "msg", 
        description = "text to send",
        paramType = ParamType.Query,
        required = true,
        allowMultiple = false,
        defaultValue = None,
        dataType = DataType("String"))
      ,Parameter(name = "lineNumber", 
        description = "Line to update",
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
    Profile("/lcd (get)", PhidgetApiService.setLcd(msg, lineNumber), true)
  }

  get("/lcd/contrast",
    summary("Updates the LCD"),
    nickname("setContrast"),
    responseClass("void"),
    endpoint("lcd/contrast"),
    notes("The contrast is darkest at 0 and brightest at 255"),
    parameters(
      Parameter(name = "value", 
        description = "contrast to set to",
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
    Profile("/lcd/contrast (get)", PhidgetApiService.setContrast(value), true)
  }

  get("/lcd/backlight",
    summary("Updates the LCD backlight"),
    nickname("setBacklight"),
    responseClass("void"),
    endpoint("lcd/backlight"),
    notes("Backlight is either on or off"),
    parameters(
      Parameter(name = "enabled", 
        description = "turn on or off light",
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
    Profile("/lcd/backlight (get)", PhidgetApiService.setBacklight(enabled), true)
  }
}
