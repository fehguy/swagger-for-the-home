package apis

import com.wordnik.util.perf.Profile

case class ApiResponse(msg: String, code: Int)

trait SwaggerDatatypeConversionSupport {
	def IntDataType(obj: String): Int = obj.toInt

	def IntDataType(obj: Option[String]): Option[Int] = obj match {
		case Some(i) => Some(i.toInt)
		case _ => None
	}

	def BooleanDataType(obj: String): Boolean = obj.toBoolean

	def StringDataType(obj: String): String = obj

	def StringDataType(obj: Option[String]): Option[String] = obj
}

object MemoryManager {
	val BYTES = 0
	val KB = 1
	val MB = 2

	def format(value: Long, factor: Int): String = {
		factor match {
			case KB => "%.2fkb".format(value / 1024.0)
			case MB => "%.2fmb".format(value / 1048576.0)
			case _ => value + "b"
		}
	}

/*
 + total memory:     30867456
 + available memory: 28594056
*/
	def status = {
		println("Memory status")
		println(" + total memory: " + format(totalMemory, MB))
		println(" + available memory: " + format(availableMemory, MB))
		println(" + % free: " + "%.2f".format(fractionAvailable * 100))
	}

	def fractionAvailable: Double = {
		availableMemory.toDouble / totalMemory.toDouble
	}

	def usedMemory: Long = {
		Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
	}

	def availableMemory: Long = {
		totalMemory - usedMemory
	}

	def availableMemory(`type`: Int): Long = {
		val div = `type` match {
			case KB => 1024.0
			case MB => 1048576.0
			case _ => 1.0
		}
		(availableMemory.toDouble / div).toLong
	}

	def totalMemory: Long = Runtime.getRuntime().maxMemory()

	def gc(aggressiveness: Int): Long = {
		val initialMem = availableMemory
		aggressiveness match {
			case i: Int if(i < 0) =>
			case _ => {
				Profile("gc(" + aggressiveness + ")", {
					(0 to aggressiveness).foreach(i => Runtime.getRuntime().gc())
				})
			}
		}
		availableMemory - initialMem
	}
}
