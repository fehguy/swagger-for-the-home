package models

import java.util.Date
case class AnalogIO (
  timestamp: Date,
  position: Int,
  name: Option[String]= None,
  value: Double)

