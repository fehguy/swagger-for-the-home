package models

import java.util.Date
case class DigitalIO (
  timestamp: Option[Date]= None,
  position: Int,
  value: Boolean)

