package services

import config._
import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

trait LCDSupport {
	println(Phidget.getLibraryVersion())

	var contrast = 200
	var backlight = false
	val lcd = new TextLCDPhidget
	var lcdAttached = {
		Configurator.hasConfig("updateLcd") match {
			case true => initLcd
			case false => false
		}
	}

	lcd.addAttachListener(new AttachListener() {
		def attached(ae: AttachEvent) = {
			println("LCD attachment of " + ae)
			lcdAttached = true
		}
	})
	lcd.addDetachListener(new DetachListener() {
		def detached(ae: DetachEvent) = {
			println("LCD detachment of " + ae)
			lcdAttached = false
		}
	})
	lcd.addErrorListener(new ErrorListener() {
		def error(ee: ErrorEvent) = {
			println("LCD error event for " + ee)
		}
	})

	def initLcd(): Boolean = {
		lcd.openAny()
		println("waiting for LCD attachment...")
		lcd.waitForAttachment()

    lcd.setScreen(0)
    lcd.setScreenSize(8)
    lcd.initialize()

    lcdAttached = true

    setContrast(contrast)
    setBacklight(backlight)
		println("attached LCD: " + lcd.getScreenCount + " " + lcd.getRowCount + "x" + lcd.getColumnCount)

    true
	}

	def setBacklight(enabled: Boolean) = {
		backlight = enabled
		lcd.setBacklight(backlight)

		ApiResponse("set backlight enabled to " + enabled, 200)
	}

	def setContrast(value: Int) = {
		contrast = value
    lcd.setContrast(contrast)

		ApiResponse("set contrast to " + value, 200)
	}

	def setLcd(value: String, lineNumber: Int) = {
    lcd.setDisplayString(lineNumber, value)

		ApiResponse("set lcd line " + lineNumber + " to " + value, 200)
	}

	def disconnectLcd = {
		lcd.close()
	}	
}