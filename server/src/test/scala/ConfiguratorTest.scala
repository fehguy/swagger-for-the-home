import config._
import models._

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ConfiguratorTest extends FlatSpec with ShouldMatchers {
  it should "create a configuration object" in {
  	val json = """
  		{
  			"values": {
  				"good": "true",
  				"healthy": "true",
  				"rich": "true",
  				"age": "40"
  			},
  			"inputZones": [
  				{
            "logicalPosition": 3,
  					"deviceId": "zyx",
  					"position": 3,
  					"name": "bedroom"
  				},
  				{
            "logicalPosition": 4,
  					"deviceId": "zyx",
  					"position": 4,
  					"name": "living room"
  				}
  			],
  			"outputZones": [
  				{
            "logicalPosition": 3,
  					"deviceId": "abcd",
  					"position": 3,
  					"name": "bedroom"
					},
  				{
            "logicalPosition": 4,
  					"deviceId": "abcd",
  					"position": 4,
  					"name": "living room"
					}
  			]
  		}
  	"""

  	val config = Configurator.load(json)
  	println(config)
  	Configurator("good") should be ("true")
  	Configurator("healthy") should be ("true")
  	Configurator.asInt("age") should be (40)
  	Configurator.inputZones.isInstanceOf[List[InputZone]] should be (true)
  	Configurator.outputZones.isInstanceOf[List[OutputZone]] should be (true)
  }
}