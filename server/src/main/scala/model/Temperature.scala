package model

case class TemperatureResponse(
  cod: String,
  message: Double,
  city: City,
  list: List[TemperatureForecast])

case class City(
  id: Long,
  name: String,
  coord: Coordinate,
  country: String,
  population: Long)

case class Coordinate(
  lon: Double,
  lat: Double)

case class TemperatureForecast(
  dt: Long,
  temp: Temperature,
  pressure: Double,
  humidity: Int,
  weather: List[WeatherDescription],
  speed: Double,
  deg: Int,
  clouds: Int)

case class WeatherDescription(
  id: Int,
  main: String,
  description: String,
  icon: String)

case class Temperature(
  day: Double,
  min: Double,
  max: Double,
  night: Double,
  eve: Double,
  morn: Double)