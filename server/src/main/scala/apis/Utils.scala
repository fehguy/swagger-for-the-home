package apis

case class ApiResponse(msg: String, code: Int)

trait DatatypeSupport {
	def IntDataType(obj: String): Int = obj.toInt

	def BooleanDataType(obj: String): Boolean = obj.toBoolean

	def StringDataType(obj: String): String = obj

	def StringDataType(obj: Option[String]): Option[String] = obj
}
