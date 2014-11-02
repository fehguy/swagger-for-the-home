package service

object PhidgetApiService extends InterfaceKitSupport with OutputRelaySupport with ConfigurationSupport {
  def setLcd(msg: String, lineNumber: Int) = {}

  def updateRelayZone(state: Boolean, name: String) = {
    val ids: Set[Int] = name match {
      case "basement" => Set(1, 2, 3, 8)
      case "middle-floor" => Set(4, 6, 7, 9, 12)
      case "upstairs" => Set(10, 11)
      case _ => Set()
    }
    for (i <- ids) setOutputRelay(state, i)
  }
}