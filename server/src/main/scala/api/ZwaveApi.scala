package api

import model.DeviceState
import model.ZWaveDevice

import service._

import org.scalatra.{ TypedParamSupport, ScalatraServlet }
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }

import scala.collection.JavaConverters._

class ZwaveApi(implicit val swagger: Swagger) extends ScalatraServlet
    with JacksonJsonSupport
    with SwaggerSupport {

  val dimmers = List(3, 4, 8)
  val switches = List(2, 5, 6)
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "ZwaveApi"
  override protected val applicationName: Option[String] = Some("zwave")

  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  error {
    case e => {
      Map("message" -> e.getMessage, "code" -> "500")
    }
  }

  val getSwitchStateOperation = (apiOperation[DeviceState]("getSwitchState")
    summary "Gets state for a switch"
    parameters (
      pathParam[Int]("deviceId").description(""))
  )

  get("/device/:deviceId", operation(getSwitchStateOperation)) {
    val deviceId: Int = params.getAs[Int]("deviceId").getOrElse(halt(400))

    ZwaveApiService.getDeviceState(deviceId)
  }

  val getDevicesOperation = (apiOperation[List[ZWaveDevice]]("getDevices")
    summary "gets devices"
    parameters ()
  )

  get("/devices", operation(getDevicesOperation)) {
    ZwaveApiService.getDevices()
  }

  val setDimmerValueOperation = (apiOperation[Unit]("setDimmerValue")
    summary "Updates"
    parameters (
      pathParam[Int]("deviceId").description("").allowableValues(dimmers), pathParam[Int]("value").description("").allowableValues(0, 25, 50, 75, 99))
  )

  post("/dimmer/:deviceId/:value", operation(setDimmerValueOperation)) {
    val deviceId: Int = params.getAs[Int]("deviceId").getOrElse(halt(400))

    val value: Int = params.getAs[Int]("value").getOrElse(halt(400))

    ZwaveApiService.setDimmerValue(deviceId, value)
  }

  val dimmerOnWithTimerOperation = (apiOperation[Unit]("dimmerOnWithTimer")
    summary "Updates"
    parameters (
      pathParam[Int]("deviceId")
      .description("")
      .allowableValues(dimmers),
      queryParam[Int]("timer")
      .description("")
      .optional
      .defaultValue(30)
      .allowableValues(5, 15, 30, 60))
  )

  post("/dimmer/:deviceId/timer", operation(dimmerOnWithTimerOperation)) {
    val deviceId: Int = params.getAs[Int]("deviceId").getOrElse(halt(400))

    val timer: Int = params.getAsOrElse[Int]("timer", halt(400))

    ZwaveApiService.dimmerOnWithTimer(deviceId, timer)
  }

  val setSwitchValueOperation = (apiOperation[Unit]("setSwitchValue")
    summary "Updates"
    parameters (
      pathParam[Int]("deviceId")
      .description("")
      .allowableValues(switches),
      pathParam[Int]("value")
      .description("").allowableValues(0, 255))
  )

  post("/switch/:deviceId/:value", operation(setSwitchValueOperation)) {
    val deviceId: Int = params.getAs[Int]("deviceId").getOrElse(halt(400))

    val value: Int = params.getAs[Int]("value").getOrElse(halt(400))

    ZwaveApiService.setSwitchValue(deviceId, value)
  }

  val switchOnWithTimerOperation = (apiOperation[Unit]("setSwitchValueWithTimer")
    summary "Updates"
    parameters (
      pathParam[Int]("deviceId")
      .description("")
      .allowableValues(switches),
      queryParam[Int]("timer").description("")
      .required
      .defaultValue(30)
      .allowableValues(List(5, 15, 30, 60)))
  )

  post("/switch/:deviceId/timer", operation(switchOnWithTimerOperation)) {
    val deviceId: Int = params.getAsOrElse[Int]("deviceId", halt(400))
    val timer: Int = params.getAsOrElse[Int]("timer", halt(400))

    ZwaveApiService.switchOnWithTimer(deviceId, timer)
  }

}
