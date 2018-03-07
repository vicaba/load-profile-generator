package infrastructure.in.config.json.deserializer

import domain.in.config.InputConfigurationScala
import infrastructure.in.serialization.json.deserializer.OptionsSerializer
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.io.Source

final class InputConfigurationJsonReaderScala(path: String) {

  implicit private val formats: Formats = DefaultFormats + OptionsSerializer

  def readJson4(): InputConfigurationScala = {
  val json = parse(Source.fromFile(path).mkString)
    print(json + "\n")
    val inputConfig = json.extract[InputConfigurationScala]
    if (inputConfig.fields(0).distributionInfo == null) {
      print("Dist is null")
    } else {
      print("Dist is not null")
    }
    inputConfig
  }

}
