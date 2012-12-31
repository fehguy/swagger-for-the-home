package models

import java.util.Date
case class AnalogIO (
  position: Int,
  value: Double,
  timestamp: Date,
  name: Option[String]= None)

