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
          InputFieldScala(info, options, distributionInfo.orNull)
        case "date" =>
          print("Found a date\n")
          val options = (x \ "options").extract[OptionsScalaDate]
          InputFieldScala(info, options, distributionInfo.orNull)
        case "integer" | "decimal" =>
          print("Found a number\n")
          val options = (x \ "options").extract[OptionsScalaNumber]
          InputFieldScala(info, options, distributionInfo.orNull)
        case null => throw new MappingException("Can't convert bar with name " + x + " to Bar")
      }
  }, {
    case x: InputFieldScala[OptionsScala] =>
      parse("{test: 0}")
  }
  ))

  /*
  implicit def conditionModifierScalaReads[T](implicit condition: Reads[T]): Reads[ConditionModifierScala[T]] = new Reads[ConditionModifierScala[T]] {
    def reads(json: JsValue): ConditionModifierScala[T] = new ConditionModifierScala[T](

      (json \ "id").as[String],
      (json \ "operation").as[String],
      (json \ "value").as[T],
    )
  }

  implicit def inputRuleScalaReads[T](implicit condition: Reads[T]): Reads[InputRuleScala[T]] = new Reads[InputRuleScala[T]] {
    def reads(json: JsValue): InputRuleScala[T] = new InputRuleScala[T](

      (json \ "id").as[String],
      (json \ "condition").as[String],
      (json \ "comparator").as[T],
      (json \ "result").as[ConditionModifierScala[T]]
    )
  }
  */

  def readJson4(): Unit = {
    val json = parse(Source.fromFile(path).mkString)
    print(json + "\n")
    val inputConfig = json.extract[InputConfigurationScala]
    if (inputConfig.getField(1).getDistributionInfo == null) {
      print("Dist is null")
    } else {
      print("Dist is not null")
    }

  }

  /*
  def readJson(): String = {
    val json: JsValue = Json.parse(Source.fromFile(path).mkString)

    //implicit val inputRuleScala: Reads[InputRuleScala[_]] = inputRuleScalaReads[_]

    implicit val inputFieldInfoScalaReads: Reads[InputFieldInfoScala] = (
      (JsPath \ "id").read[String] and
        (JsPath \ "name").read[String] and
        (JsPath \ "type").read[String]
      ) (InputFieldInfoScala.apply _)

    implicit val optionsScalaString: Reads[OptionsScalaString] = (JsPath \ "acceptedStrings")
      .read[Seq[String]].map(acceptedStrings => OptionsScalaString(acceptedStrings))

    implicit val numberRangesScala: Reads[NumberRangeScala] = (
      (JsPath \ "min").read[Float] and
        (JsPath \ "max").read[Float]
      ) (NumberRangeScala.apply _)

    implicit val optionsScalaNumber: Reads[OptionsScalaNumber] = (JsPath \ "ranges")
      .read[Seq[NumberRangeScala]].map(ranges => OptionsScalaNumber(ranges))

    val dateReads: Reads[OptionsScalaDate] = verifying[OptionsScalaDate](_.getClass.getSimpleName == "OptionsScalaDate")(Json.reads[OptionsScalaDate])
    val stringReads: Reads[OptionsScalaString] = verifying[OptionsScalaString](_.getClass.getSimpleName == "OptionsScalaString")(Json.reads[OptionsScalaString])
    val numberReads: Reads[OptionsScalaNumber] = verifying[OptionsScalaNumber](_.getClass.getSimpleName == "OptionsScalaNumber")(Json.reads[OptionsScalaNumber])
    implicit val optionsRead: Reads[OptionsScala] = dateReads.map(identity[OptionsScala]) or stringReads.map(identity[OptionsScala]) or numberReads.map(identity[OptionsScala])

    implicit val distributionInfoScala: Reads[DistributionInfoScala] = (
      ((JsPath \ "distributionMethod").read[String] or Reads.pure("")) and
        ((JsPath \ "offset").read[Double] or Reads.pure(0.0)) and
        ((JsPath \ "totalData").read[Double] or Reads.pure(0.0))
      ) (DistributionInfoScala.apply _)

    implicit val inputFieldScalaReads: Reads[InputFieldScala] = (
      (JsPath \ "info").read[InputFieldInfoScala] and
        (JsPath \ "options").read[OptionsScala] and
        (JsPath \ "distributionInfo").read[DistributionInfoScala]
      ) (InputFieldScala.apply _)

    implicit val inputConfigurationScala: Reads[InputConfigurationScala] = (
      (JsPath \ "fields").read[Seq[InputFieldScala]] and
        (JsPath \ "rules").read[Seq[InputRuleScala[_]]]
      ) (InputConfigurationScala.apply _)

    val inputConfiguration = json.as[InputConfigurationScala]

    val fieldInfo = inputConfiguration.getFields
      .map(field => "Field: " + field.getInfo.getId + " - " + field.getInfo.getName + " - " + field.getInfo.getType + "\n").mkString
    val distributionInfo = inputConfiguration.getFields
      .filter(field => !field.getDistributionInfo.getDistributionMethod.isEmpty)
      .map(field => field.getDistributionInfo.getDistributionMethod + " - " + field.getDistributionInfo.getOffset + " - " + field.getDistributionInfo.getTotalData + "\n").mkString
    val options = inputConfiguration.getFields
      .map(field => field.getOptions.toString).mkString("\n")
    fieldInfo + "\n" + distributionInfo + "\n" + options
  }
  */
}
