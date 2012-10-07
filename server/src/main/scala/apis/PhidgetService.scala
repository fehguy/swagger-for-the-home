package apis

import com.phidgets._
import com.phidgets.TextLCDPhidget._
import com.phidgets.event._


object PhidgetService {
	println(Phidget.getLibraryVersion())

	var contrast = 200
	var backlight = true
	val lcd = new TextLCDPhidget

	initLcd()

	def initLcd() = {
		lcd.addAttachListener(new AttachListener() {
			def attached(ae: AttachEvent) = {
				println("attachment of " + ae)
			}
		});
		lcd.addDetachListener(new DetachListener() {
			def detached(ae: DetachEvent) = {
				println("detachment of " + ae)
			}
		});
		lcd.addErrorListener(new ErrorListener() {
			def error(ee: ErrorEvent) = {
				println("error event for " + ee)
			}
		})

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

    setContrast(contrast)
    setBacklight(backlight)
	}

//	PhidgetService.setBacklight(params.getOrElse("enabled", halt(400)).toBoolean)
	def setBacklight(enabled: Boolean) = {
		backlight = enabled
		lcd.setBacklight(backlight)
	}

//	PhidgetService.setContrast(params.getOrElse("value", halt(400)).toInt)
	def setContrast(value: Int) = {
		contrast = value
    lcd.setContrast(contrast)
	}

//	PhidgetService.toLcd(params.getOrElse("lineNumber", halt(400)).toInt, params("msg"))
	def toLcd(lineNumber: Int, msg: String) = {
		println("updating " + lineNumber + " to " + msg)
    lcd.setDisplayString(lineNumber, msg)
	}

	def close = {
		print("closing...\n")
		lcd.close()
	}
}