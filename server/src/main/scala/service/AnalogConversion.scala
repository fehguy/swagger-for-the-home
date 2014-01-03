package service

trait AnalogConversion {
  def bitsToVoltage(input: Int): Double = input.toDouble * 5.0 / 4095.0 * 100.0
}
