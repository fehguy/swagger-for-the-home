package apis

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
