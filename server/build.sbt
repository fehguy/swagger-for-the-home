import AssemblyKeys._ // put this at the top of the file

assemblySettings

organization := "org.eatbacon"

name := "sfth"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

classpathTypes ~= (_ + "orbit")

libraryDependencies ++= Seq(
  "com.phidgets"                 % "phidget"                % "2.1.0",
  "org.scalatra"                 % "scalatra"               % "2.2.0-SNAPSHOT",
  "org.scalatra"                 % "scalatra-scalate"       % "2.2.0-SNAPSHOT",
  "org.scalatra"                 % "scalatra-specs2"        % "2.2.0-SNAPSHOT"      % "test",
  "org.scalatra"                 % "scalatra-swagger"       % "2.2.0-SNAPSHOT",
  "org.scalatra"                 % "scalatra-json"          % "2.2.0-SNAPSHOT",
  "org.json4s"                  %% "json4s-jackson"         % "3.1.0-SNAPSHOT",
  "org.mongodb"                  % "mongo-java-driver"      % "2.9.1",
  "com.wordnik"                  % "swagger-core_2.9.1"     % "1.1.1-SNAPSHOT",
  "com.wordnik"                  % "mongo-utils_2.9.1"      % "1.1.3",
  "com.novus"                   %% "salat"                  % "1.9.1",
  "org.eclipse.jetty"            % "jetty-webapp"           % "8.1.7.v20120910"     % "compile",
  "org.eclipse.jetty.orbit"      % "javax.servlet"          % "3.0.0.v201112011016" % "compile;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "about.html"     => MergeStrategy.discard
    case x => old(x)
  }
}
