name := "TFG"

version := "0.1"

scalaVersion := "2.12.3"

//https://mvnrepository.com/artifact/com.google.code.gson/gson
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.4",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.4" % Test
)







        