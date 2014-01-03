package model

case class InputZone(
  name: String,
  logicalPosition: Int,
  position: Int,
  inputDeviceId: String,
  /**
   * The ID of the device controlling this zone
   */
  outputDeviceId: String)

