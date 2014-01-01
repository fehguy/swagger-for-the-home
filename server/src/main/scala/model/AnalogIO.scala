package model

import java.util.Date
case class AnalogIO (
  name: Option[String],

  position: Int,

  value: Double,

  timestamp: Date

  )

