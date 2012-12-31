package models

import java.util.Date

case class AnalogIO (
  position: Int,
  value: Double,
  timestamp: Date,
  name: Option[String]= None)

case class AnalogSample(position: Int,
	average: Double, 
	stdDev: Double, 
	timestamp: Date,
  name: Option[String]= None)

case class InputZone (
  deviceId: String,
  position: Int,
  logicalPosition: Int,
  name: String)
