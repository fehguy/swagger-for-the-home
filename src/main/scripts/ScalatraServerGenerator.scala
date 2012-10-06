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

    ("project/build.properties",      outputFolder + "/project",                     "build.properties"),
    ("project/plugins.sbt",           outputFolder + "/project",                     "plugins.sbt"),

    ("JettyMain.scala",               outputFolder + "/src/main/scala",             "JettyMain.scala"),
    ("web.xml",                       outputFolder + "/target/webapp/WEB-INF",      "web.xml"),    
    ("admin/index.html",              outputFolder + "/target/webapp/admin",        "index.html"),
    ("admin/swagger-ui.js",           outputFolder + "/target/webapp/admin",        "swagger-ui.js"),
    ("admin/swagger-ui.min.js",       outputFolder + "/target/webapp/admin",        "swagger-ui.min.js"),

    ("admin/css/screen.css",          outputFolder + "/target/webapp/admin/css",    "screen.css"),

    ("admin/images/logo_small.png",   outputFolder + "/target/webapp/admin/images", "logo_small.png"),
    ("admin/images/pet_store_api.png",outputFolder + "/target/webapp/admin/images", "pet_store_api.png"),
    ("admin/images/throbber.gif",     outputFolder + "/target/webapp/admin/images", "throbber.gif"),
    ("admin/images/wordnik_api.png",  outputFolder + "/target/webapp/admin/images", "wordnik_api.png"),

    ("admin/lib/backbone-min.js",     outputFolder + "/target/webapp/admin/lib",    "backbone-min.js"),
    ("admin/lib/handlebars.runtime-1.0.0.beta.6.js", 
                                      outputFolder + "/target/webapp/admin/lib",    "handlebars.runtime-1.0.0.beta.6.js"),
    ("admin/lib/jquery-1.8.0.min.js", outputFolder + "/target/webapp/admin/lib",    "jquery-1.8.0.min.js"),
    ("admin/lib/jquery.ba-bbq.min.js",outputFolder + "/target/webapp/admin/lib",    "jquery.ba-bbq.min.js"),
    ("admin/lib/jquery.slideto.min.js",
                                      outputFolder + "/target/webapp/admin/lib",    "jquery.slideto.min.js"),
    ("admin/lib/jquery.wiggle.min.js",outputFolder + "/target/webapp/admin/lib",    "jquery.wiggle.min.js"),
    ("admin/lib/swagger.js",          outputFolder + "/target/webapp/admin/lib",    "swagger.js"),
    ("admin/lib/underscore-min.js",   outputFolder + "/target/webapp/admin/lib",    "underscore-min.js")
    )

  override def toDefaultValue(datatype: String, v: String): Option[String] = {
    if (v != "" && v != null) {
      datatype match {
        case "int" => Some("\"" + v + "\"")
        case "long" => Some("\"" + v + "\"")
        case "double" => Some("\"" + v + "\"")
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
        // the scalatra templates like lower-case httpMethods
        case e: String if (e == "httpMethod") => mutable += "httpMethod" -> k._2.toString.toLowerCase

        // convert path into ruby-ish syntax without basePart (i.e. /pet.{format}/{petId} => /:petId
        case e: String if (e == "path") => {
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
