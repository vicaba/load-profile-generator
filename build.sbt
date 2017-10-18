import Dependencies._

name := "TFG"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  gson,
  akkaStream,
  akkaStreamTest,
  apacheFreemaker
)
