package infrastructure.in.config.json.deserializer

import domain.in.field.options.{NumberRangeScala, _}
import domain.in.field.{DistributionInfoScala, InputFieldInfoScala, InputFieldScala}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.verifying

import scala.io.Source

final class InputConfigurationJsonReaderScala(path: String) {
  def read(): String = {
    val json: JsValue = Json.parse(Source.fromFile(path).mkString)

    implicit val inputFieldInfoScalaReads: Reads[InputFieldInfoScala] = (
      (JsPath \ "id").read[String] and
        (JsPath \ "name").read[String] and
        (JsPath \ "type").read[String]
      )(InputFieldInfoScala.apply _)

    implicit val optionsScalaString: Reads[OptionsScalaString] = (JsPath \ "acceptedStrings")
      .read[Seq[String]].map(acceptedStrings => OptionsScalaString(acceptedStrings))

    implicit val numberRangesScala: Reads[NumberRangeScala] = (
      (JsPath \ "min").read[Float] and
        (JsPath \ "max").read[Float]
    )(NumberRangeScala.apply _)

    implicit val optionsScalaNumber: Reads[OptionsScalaNumber] = (JsPath \ "ranges")
      .read[Seq[NumberRangeScala]].map(ranges => OptionsScalaNumber(ranges))

    val dateReads: Reads[OptionsScalaDate] = verifying[OptionsScalaDate](_.getClass.getSimpleName == "OptionsScalaDate")(Json.reads[OptionsScalaDate])
    val stringReads: Reads[OptionsScalaString] = verifying[OptionsScalaString](_.getClass.getSimpleName == "OptionsScalaString")(Json.reads[OptionsScalaString])
    val numberReads: Reads[OptionsScalaNumber] = verifying[OptionsScalaNumber](_.getClass.getSimpleName == "OptionsScalaNumber")(Json.reads[OptionsScalaNumber])
    implicit val optionsRead: Reads[OptionsScala] = dateReads.map(identity[OptionsScala]) or stringReads.map(identity[OptionsScala]) or numberReads.map(identity[OptionsScala])

    implicit val distributionInfoScala: Reads[DistributionInfoScala] = (
      ((JsPath \ "distributionMethod").read[String] or Reads.pure("") ) and
        ((JsPath \ "offset").read[Double] or Reads.pure(0.0) ) and
        ((JsPath \ "totalData").read[Double]  or Reads.pure(0.0) )
      )(DistributionInfoScala.apply _)

    implicit val inputFieldScalaReads: Reads[InputFieldScala] = (
      (JsPath \ "info").read[InputFieldInfoScala] and
        (JsPath \ "options").read[OptionsScala] and
        (JsPath \ "distributionInfo").read[DistributionInfoScala]
      )(InputFieldScala.apply _)

    val inputFieldInfo = (json \ "fields").as[Seq[InputFieldScala]]

    val fieldInfo = inputFieldInfo
      .map(field => "Field: "+ field.getInfo.getId + " - " + field.getInfo.getName + " - " + field.getInfo.getType + "\n").mkString
    val distributionInfo = inputFieldInfo
      .filter(field => !field.getDistributionInfo.getDistributionMethod.isEmpty)
      .map(field => field.getDistributionInfo.getDistributionMethod + " - " + field.getDistributionInfo.getOffset + " - " + field.getDistributionInfo.getTotalData + "\n").mkString
    val options = inputFieldInfo
      .map(field => field.getOptions.toString).mkString("\n")
    fieldInfo + "\n" + distributionInfo + "\n" + options
  }
}
