package models

import java.util.Date

case class AnalogIO (
  position: Int,
  timestamp: Date,
  value: Double,
  name: Option[String]= None)

case class AnalogSample(position: Int,
	average: Double, 
	stdDev: Double, 
	timestamp: Date,
  name: Option[String]= None)
