package models

import java.util.Date
case class DigitalIO (
  position: Int,
  value: Boolean,
  timestamp: Option[Date]= None)

