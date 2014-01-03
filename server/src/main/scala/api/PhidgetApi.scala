package api

import service._

import model.InputZone
import model.DigitalIO
import model.AnalogIO
import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }

import scala.collection.JavaConverters._

class PhidgetApi(implicit val swagger: Swagger) extends ScalatraServlet
    with JacksonJsonSupport
    with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "PhidgetApi"
  override protected val applicationName: Option[String] = Some("phidget")

  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  error {
    case e => {
      Map("message" -> e.getMessage, "code" -> "500")
    }
  }

  val getInputZonesOperation = (apiOperation[List[InputZone]]("getInputZones")
    summary "reads the input definitions based on configs"
    parameters ()
  )

  get("/inputDefinition", operation(getInputZonesOperation)) {
    PhidgetApiService.getInputZones()
  }

  val getAnalogInputsOperation = (apiOperation[List[AnalogIO]]("getAnalogInputs")
    summary "returns all inputs"
    parameters ()
  )

  get("/analog/inputs", operation(getAnalogInputsOperation)) {
    PhidgetApiService.getAnalogInputs()
  }

  val setLcdOperation = (apiOperation[Unit]("setLcd")
    summary "Updates the LCD"
    parameters (
      queryParam[String]("msg").description(""), queryParam[Int]("lineNumber").description("").allowableValues(0, 1, 2, 3))
  )

  post("/lcd", operation(setLcdOperation)) {
    val msg: String = params.getAs[String]("msg").getOrElse(halt(400))
    val lineNumber: Int = params.getAs[Int]("lineNumber").getOrElse(halt(400))
    PhidgetApiService.setLcd(msg, lineNumber)
  }

  val setAllRelayOutputOperation = (apiOperation[Unit]("setAllRelayOutput")
    summary "sets all relays"
    parameters (
      queryParam[Boolean]("state").description("").allowableValues(true, false))
  )

  post("/relay/output", operation(setAllRelayOutputOperation)) {
    val state: Boolean = params.getAs[Boolean]("state").getOrElse(halt(400))
    PhidgetApiService.setAllRelayOutput(state)
  }

  val setOutputRelayOperation = (apiOperation[DigitalIO]("setOutputRelay")
    summary "sets a relay for a specific position"
    parameters (
      queryParam[Boolean]("state").description("").allowableValues(true, false), pathParam[Int]("position").description("").defaultValue(0).allowableValues(0, 1, 2, 3, 4, 5, 6, 7))
  )

  post("/relay/output/:position", operation(setOutputRelayOperation)) {
    val state: Boolean = params.getAs[Boolean]("state").getOrElse(halt(400))
    val position: Int = params.getAs[Int]("position").getOrElse(halt(400))
    PhidgetApiService.setOutputRelay(state, position)
  }

  val getRelayOutputOperation = (apiOperation[DigitalIO]("getRelayOutput")
    summary "gets an output state"
    parameters (
      pathParam[Int]("position").description("").defaultValue(0).allowableValues(0, 1, 2, 3, 4, 5, 6, 7))
  )

  get("/relay/output/:position", operation(getRelayOutputOperation)) {
    val position: Int = params.getAs[Int]("position").getOrElse(halt(400))
    PhidgetApiService.getRelayOutput(position)
  }

}
