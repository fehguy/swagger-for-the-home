import com.wordnik.swagger.codegen.BasicScalaGenerator

import scala.collection.mutable.{ HashMap, ListBuffer }

object ScalatraServerGenerator extends BasicScalaGenerator {
  def main(args: Array[String]) = generateClient(args)

  override def templateDir = "src/main/templates/scalatra"

  val outputFolder = "server"

  // where to write generated code
  override def destinationDir = outputFolder + "/src/main/scala"

  override def modelPackage = Some("org.eatbacon.model")

  override def packageName = "org.eatbacon.app"

  // template used for apis
  apiTemplateFiles ++= Map("api.mustache" -> ".scala")

  modelTemplateFiles ++= Map("model.mustache" -> ".scala")

  override def apiPackage = Some("apis")

  // supporting classes
  override def supportingFiles = List(
    ("Bootstrap.mustache",            destinationDir,                               "ScalatraBootstrap.scala"),
    ("ServletApp.mustache",           destinationDir,                               "ServletApp.scala"),

    ("README.mustache",               outputFolder,                                 "README.md"),
    ("build.sbt",                     outputFolder,                                 "build.sbt"),

    ("project",                       outputFolder + "/project",                     ""),

    ("JettyMain.scala",               outputFolder + "/src/main/scala",             "JettyMain.scala"),
    ("web.xml",                       outputFolder + "/target/webapp/WEB-INF",      "web.xml"),    
    ("admin",                         outputFolder + "/target/webapp/admin",        ""))

  /**
   * quoting all as strings
   **/
  override def toDefaultValue(datatype: String, v: String): Option[String] = {
    if (v != "" && v != null) {
      datatype match {
        case "int" => Some("\"" + v + "\"")
        case "long" => Some("\"" + v + "\"")
        case "double" => Some("\"" + v + "\"")
        case "boolean" => Some("\"" + v + "\"")
        case x if x == "string" || x == "String" => {
          v match {
            case e: String => Some("\"" + v + "\"")
            case _ => None
          }
        }
        case _ => None
      }
    } else None
  }


  override def processApiMap(m: Map[String, AnyRef]): Map[String, AnyRef] = {
    val mutable = scala.collection.mutable.Map() ++ m

    mutable.map(k => {
      k._1 match {
        case "allParams" => {
          val paramList = k._2.asInstanceOf[List[_]]
          paramList.foreach(param => {
            val map = param.asInstanceOf[scala.collection.mutable.HashMap[String, AnyRef]]
            if(map.contains("required")) {
              if(map("required") == "false") map += "notRequired" -> "true"
            }
            if(map.contains("allowableValues")) {
              val allowableValues = map("allowableValues")
              val quote = map("swaggerDataType") match {
                case "string" => "\""
                case _ => ""
              }
              val pattern = "([A-Z]*)\\[(.*)\\]".r
              val str = allowableValues match {
                case pattern(valueType, values) => {
                  valueType match {
                    case "LIST" => {
                      val l = values.split(",").toList
                      Some("AllowableValues(" + l.mkString(quote, quote + "," + quote, quote + ")"))
                    }
                    case "RANGE" => {
                      val r = values.split(",")
                      Some("AllowableValues(Range(" + r(0) + "," + r(1) + ", 1))")
                    }
                  }
                }
                case _ => None
              }
              str match {
                case Some(s) => map += "allowableValues" -> s
                case _ =>
              }
              println("default value: " + map.getOrElse("defaultValue", "not found!"))
            }
          })
        }

        // the scalatra templates like lower-case httpMethods
        case "httpMethod" => mutable += "httpMethod" -> k._2.toString.toLowerCase

        // convert path into ruby-ish syntax without basePart (i.e. /pet.{format}/{petId} => /:petId
        case "path" => {
          val path = {
            val arr = k._2.toString.split("/")
            if (arr.length >= 2) {
              mutable += "basePart" -> (arr.slice(2, arr.length).mkString("", "/", ""))
              "/" + arr.slice(2, arr.length).mkString("", "/", "")
            } else
              k._2.toString
          }
          // rip out the root path
          mutable += "path" -> path.replaceAll("\\{", ":").replaceAll("\\}", "")
        }
        case _ =>
      }
    })
    mutable.toMap
  }
}
