package service

import config._

trait ConfigurationSupport {
  def getInputZones() = {
    Configurator.config.inputZones
  }
}