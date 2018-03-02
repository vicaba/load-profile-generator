package infrastructure.in.config.json.deserializer

import domain.in.config.InputConfigurationScala
import domain.in.field.options._
import domain.in.field.{DistributionInfoScala, InputFieldInfoScala, InputFieldScala}
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.io.Source

final class InputConfigurationJsonReaderScala(path: String) {

  implicit val formats: Formats = DefaultFormats + OptionsSerializer

  object OptionsSerializer extends CustomSerializer[InputFieldScala[OptionsScala]](_ => ( {

    case x: JObject =>
      val distributionInfo = (x \ "distributionInfo").extractOpt[DistributionInfoScala]
      val info = (x \ "info").extract[InputFieldInfoScala]
      info.getType match {
        case "string" =>
          print("Found a string\n")
          val options = (x \ "options").extract[OptionsScalaString]
          new InputFieldScala(info, options, distributionInfo.orNull)
        case "date" =>
          print("Found a date\n")
          val options = (x \ "options").extract[OptionsScalaDate]
          new InputFieldScala(info, options, distributionInfo.orNull)
        case "integer" | "decimal" =>
          print("Found a number\n")
          val options = (x \ "options").extract[OptionsScalaNumber]
          new InputFieldScala(info, options, distributionInfo.orNull)
        case null => throw new MappingException("Can't convert bar with name " + x + " to Bar")
      }
  }, {
    case x: InputFieldScala[OptionsScala] =>
      parse("{test: 0}")
  }
  ))

  def readJson4(): Unit = {
    val json = parse(Source.fromFile(path).mkString)
    print(json + "\n")
    val inputConfig = json.extract[InputConfigurationScala]
    if (inputConfig.getField(0).getDistributionInfo == null) {
      print("Dist is null")
    } else {
      print("Dist is not null")
    }

  }

}
