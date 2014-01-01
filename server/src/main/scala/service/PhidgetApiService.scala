package service

object PhidgetApiService 
  extends InterfaceKitSupport
  with OutputRelaySupport
  with ConfigurationSupport {
  def setLcd(msg: String, lineNumber: Int) = {}
}