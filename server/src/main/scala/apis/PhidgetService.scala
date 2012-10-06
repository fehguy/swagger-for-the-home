package apis

object PhidgetService {
	def toLcd(lineNumber: Int, msg: String) = {
		println("updating " + lineNumber + " to " + msg)
	}
}