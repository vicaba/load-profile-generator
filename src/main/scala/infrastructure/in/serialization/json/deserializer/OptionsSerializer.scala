package infrastructure.in.serialization.json.deserializer

import domain.in.field.options._
import domain.in.field.{DistributionInfoScala, InputFieldInfoScala, InputFieldScala}
import org.json4s.jackson.JsonMethods.parse
import org.json4s.{CustomSerializer, DefaultFormats, Formats, JObject, MappingException}

object OptionsSerializer extends CustomSerializer[InputFieldScala](_ => ( {

  case x: JObject =>
    implicit val formats: Formats = DefaultFormats

    val distributionInfo = (x \ "distributionInfo").extractOpt[DistributionInfoScala]
    val info = (x \ "info").extract[InputFieldInfoScala]
    info.getType match {
      case "string" =>
        val options = (x \ "options").extract[OptionsScalaString]
        new InputFieldScala(info, options, distributionInfo.orNull)

      case "date" =>
        val options = (x \ "options").extract[OptionsScalaDate]
        new InputFieldScala(info, options, distributionInfo.orNull)

      case "integer" | "decimal" =>
        val options = (x \ "options").extract[OptionsScalaNumber]
        new InputFieldScala(info, options, distributionInfo.orNull)

      case "appliance" =>
        val options = (x \ "options").extract[OptionsScalaAppliance]
        new InputFieldScala(info, options, distributionInfo.orNull)

      case null => throw new MappingException("Can't convert options with name " + x + " to OptionsScala")
    }
}, {
  case x: InputFieldScala =>
    parse("{test: 0}")
}
))
