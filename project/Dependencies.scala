import sbt._

object Dependencies {

  val akkaVersion: String = "2.5.6"

  val gson: ModuleID = "com.google.code.gson" % "gson" % "2.8.1"

  val akkaStream: ModuleID = "com.typesafe.akka" %% "akka-stream" % akkaVersion

  val akkaStreamTest: ModuleID = "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test

  val apacheFreemaker: ModuleID = "org.freemarker" % "freemarker" % "2.3.26-incubating"

}
