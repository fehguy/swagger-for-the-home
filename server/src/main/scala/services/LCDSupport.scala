package services

import apis.ApiResponse

import com.phidgets._
import com.phidgets.event._

trait LCDSupport {
	println(Phidget.getLibraryVersion())

	var contrast = 200
	var backlight = false
	val lcd = new TextLCDPhidget
	var lcdAttached = false

	lcd.addAttachListener(new AttachListener() {
		def attached(ae: AttachEvent) = {
			println("attachment of " + ae)
			lcdAttached = true
		}
	})
	lcd.addDetachListener(new DetachListener() {
		def detached(ae: DetachEvent) = {
			println("detachment of " + ae)
			lcdAttached = false
		}
	})
	lcd.addErrorListener(new ErrorListener() {
		def error(ee: ErrorEvent) = {
			println("error event for " + ee)
		}
	})

	def initLcd(): Unit = {
		lcd.openAny()
		println("waiting for LCD attachment...")
		lcd.waitForAttachment()

		println("Phidget Information")
		println("====================================")
		println("Version: " + lcd.getDeviceVersion())
		println("Name: " + lcd.getDeviceName())
		println("Serial #: " + lcd.getSerialNumber())
		println("# Rows: " + lcd.getRowCount())
		println("# Columns: " + lcd.getColumnCount())

    println("# Screens: " + lcd.getScreenCount())

    lcd.setScreen(0)
    lcd.setScreenSize(8)
    lcd.initialize()

    lcdAttached = true

    setContrast(contrast)
    setBacklight(backlight)
	}

	def setBacklight(enabled: Boolean) = {
		if(!lcdAttached)
			initLcd()
		backlight = enabled
		lcd.setBacklight(backlight)

		ApiResponse("set backlight enabled to " + enabled, 200)
	}

	def setContrast(value: Int) = {
		if(!lcdAttached)
			initLcd()
		contrast = value
    lcd.setContrast(contrast)

		ApiResponse("set contrast to " + value, 200)
	}

	def setLcd(value: String, lineNumber: Int) = {
		if(!lcdAttached)
			initLcd()
    lcd.setDisplayString(lineNumber, value)

		ApiResponse("set lcd line " + lineNumber + " to " + value, 200)
	}

	def disconnectLcd = {
		lcdAttached = false
		lcd.close()
	}	
}