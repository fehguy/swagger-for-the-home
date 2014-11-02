package model

import java.util.Date
case class DigitalIO(
  name: Option[String],
  timestamp: Option[Date],
  position: Int,
  value: Boolean)

