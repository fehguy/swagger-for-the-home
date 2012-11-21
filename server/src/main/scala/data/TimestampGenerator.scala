package data

import java.util.Date
import java.text.SimpleDateFormat

trait TimestampGenerator {
  def timestampString(date: Option[Date] = None): String = {
    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss")
    dateFormatter.format(date match {
      case Some(date) => date
      case None => new Date
    })
  }
}