package service

import model._

import org.json4s._
import org.json4s.jackson.Serialization._
import org.json4s.jackson.JsonMethods._

import java.net.{ HttpURLConnection, URL }
import java.io.{ DataOutputStream, BufferedReader, InputStreamReader }

object EnvironmentApiService {
  implicit val formats = DefaultFormats

  def getForecast(days: Int) = {
    println("forcast: " + days)
    val url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Palo%20Alto&cnt=" + days + "&mode=json&units=imperial")
    println(url)
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.setInstanceFollowRedirects(false)
    connection.setRequestMethod("GET")
    connection.setRequestProperty("Content-Length", "0")
    connection.setUseCaches(false)

    val wr = new DataOutputStream(connection.getOutputStream())
    wr.flush()

    val reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
    var line: String = ""
    val buffer = new StringBuffer
    while (line != null) {
      line = reader.readLine()
      if (line != null)
        buffer.append(line)
    }

    val json = parse(buffer.toString)
    val forecastResponse = json.extract[TemperatureResponse]
    forecastResponse.list
  }
}