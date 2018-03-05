package domain.source.generator

import java.time.LocalDateTime

import domain.in.field.InputFieldScala
import domain.in.field.options._
import domain.transform.calculations.equal.{DateEqualCalculationsScala, NumberEqualCalculationsScala, StringEqualCalculationsScala}
import domain.value.generator._

object SourceGeneratorFactoryScala {
  def createGeneratorFromInput(in: InputFieldScala[OptionsScala]): ValueGeneratorScala[_, _] = in.getOptions match {
    case _: OptionsScalaString =>
      new StringValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[String]],
        new StringEqualCalculationsScala(in.getOptions.asInstanceOf[OptionsScalaString].getAcceptedStrings))

    case _: OptionsScalaNumber =>
      new NumberValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[Float]],
        new NumberEqualCalculationsScala(in.getOptions.asInstanceOf[OptionsScalaNumber].getRanges)
      )

    case _: OptionsScalaDate =>
      new DateValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[LocalDateTime]],
        new DateEqualCalculationsScala(
          in.getOptions.asInstanceOf[OptionsScalaDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsScalaDate].getTimeIncrement)
      )
  }
}