package infrastructure.in.serialization.json.deserializer

import domain.in.field.options.{OptionsScala, OptionsScalaDate, OptionsScalaNumber, OptionsScalaString}
import domain.in.field.{DistributionInfoScala, InputFieldInfoScala, InputFieldScala}
import org.json4s.jackson.JsonMethods.parse
import org.json4s.{CustomSerializer, DefaultFormats, Formats, JObject, MappingException}

object OptionsSerializer extends CustomSerializer[InputFieldScala[OptionsScala]](_ => ( {

  case x: JObject =>
    implicit val formats: Formats = DefaultFormats

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
