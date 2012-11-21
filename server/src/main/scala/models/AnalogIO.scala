package models

import java.util.Date
case class AnalogIO (
  timestamp: Date,
  position: Int,
  value: Double,
  name: Option [String] = None)

