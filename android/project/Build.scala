import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "SwaggerForTheHome",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-16"
  )

  val proguardSettings = Seq (
    useProguard in Android := true,
    proguardOptimizations in Android := Seq(
      "-dontoptimize", // TODO revise later
      //"-dontshrink",
      "-dontskipnonpubliclibraryclasses",
      "-ignorewarnings"
    ),
    proguardOption in Android :=
      """
        |-dontwarn scala.**
        |-keepclassmembers class * {
        |    ** MODULE$;
        |}
        |-keep class scala.collection.SeqLike {
        |    public protected *;
        |}
        |-keep public class * extends android.app.Activity
        |-keep public class * extends android.app.Application
        |-keep public class * extends android.app.Service
        |-keep public class * extends android.content.BroadcastReceiver
        |-keep public class * extends android.content.ContentProvider
        |-keep public class * extends android.app.backup.BackupAgentHelper
        |-keep public class * extends android.preference.Preference
        |-keep public class com.android.vending.licensing.ILicensingService
        |-keep public class org.eatbacon.sfth.AnalogUpdateActivity
        |-keep public class org.eatbacon.sfth.UpdateDataTask
        |-keep public class org.eatbacon.sfth.ShowChartActivity
        |
        |-keepclasseswithmembernames class * {
        |    native <methods>;
        |}
        |
        |-keepclasseswithmembernames class * {
        |    public <init>(android.content.Context, android.util.AttributeSet);
        |}
        |
        |-keepclasseswithmembernames class * {
        |    public <init>(android.content.Context, android.util.AttributeSet, int);
        |}
        |
        |-keepclassmembers enum * {
        |    public static **[] values();
        |    public static ** valueOf(java.lang.String);
        |}
        |
        |-keep class * implements android.os.Parcelable {
        |  public static final android.os.Parcelable$Creator *;
        |}
      """.stripMargin
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      resolvers ++= Seq("Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"),
      libraryDependencies ++= Seq(
        "org.scalatest"               %% "scalatest"              % "1.8"     % "test",
        "com.wordnik"                  % "common-utils_2.9.1"     % "1.1.4",
        "org.achartengine"             % "achartengine"           % "1.0.0",
        "org.json4s"                  %% "json4s-native"          % "3.0.0",
        "com.github.chrisbanes.pulltorefresh" % "library"         % "2.0.1",
        "org.json4s"                  %% "json4s-jackson"         % "3.0.0"
      )
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "SwaggerForTheHome",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "SwaggerForTheHomeTests"
    )
  ) dependsOn main
}
