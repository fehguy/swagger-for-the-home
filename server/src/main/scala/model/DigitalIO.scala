package model

import java.util.Date
case class DigitalIO (
  name: Option[String],

  position: Int,

  value: Boolean,

  timestamp: Option[Date]

  )

