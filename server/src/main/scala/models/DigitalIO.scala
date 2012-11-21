package models

case class DigitalIO (
  position: Int,
  value: Boolean,
  timestamp: Option[java.util.Date] = None)
