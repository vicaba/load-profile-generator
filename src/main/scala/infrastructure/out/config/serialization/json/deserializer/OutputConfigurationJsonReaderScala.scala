package infrastructure.out.config.serialization.json.deserializer

import domain.out.field.OutputFieldScala
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, Formats, _}

import scala.io.Source

final class OutputConfigurationJsonReaderScala(path: String) {
  implicit private val formats: Formats = DefaultFormats

  def readJson4(): OutputFieldScala = {
    val json = parse(Source.fromFile(path).mkString)
    print(json + "\n")
    val outputField = json.extract[OutputFieldScala]
    print(outputField.nameTemplate + " - " + outputField.outputType)
    outputField
  }
}
