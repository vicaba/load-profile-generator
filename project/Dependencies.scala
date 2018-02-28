import sbt._

object Dependencies {

  val akkaVersion: String = "2.5.6"

  val gson: ModuleID = "com.google.code.gson" % "gson" % "2.8.1"

  val akkaStream: ModuleID = "com.typesafe.akka" %% "akka-stream" % akkaVersion

  val akkaStreamTest: ModuleID = "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test

  val apacheFreemaker: ModuleID = "org.freemarker" % "freemarker" % "2.3.26-incubating"

  val scalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"

  val logback: ModuleID = "ch.qos.logback" % "logback-classic" % "1.2.3"

  val apacheCommons: ModuleID = "org.apache.commons" % "commons-math3" % "3.6.1"

  val playJson: ModuleID = "com.typesafe.play" %% "play-json" % "2.6.7"

}
