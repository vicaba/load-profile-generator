package domain.distribution.generator

import java.time.LocalDateTime

import domain.in.field.InputFieldScala
import domain.in.field.options._
import domain.transform.calculations.distribution.{DateDistributionCalculationsScala, NumberDistributionCalculationsScala}
import domain.transform.calculations.equal.StringEqualCalculationsScala
import domain.value.generator._

object DistributionGeneratorFactoryScala {

  def createGeneratorFromInput(in: InputFieldScala[_]): ValueGeneratorScala[_, _] = in.getOptions match {
    case _: OptionsScalaString =>
      new StringValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[String]],
        new StringEqualCalculationsScala(in.getOptions.asInstanceOf[OptionsScalaString].getAcceptedStrings))

    case _: OptionsScalaNumber =>
      new NumberValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[Float]],
        new NumberDistributionCalculationsScala(
          in.getOptions.asInstanceOf[OptionsScalaNumber].getRanges,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )

    case _: OptionsScalaDate =>
      new DateValueGeneratorScala(
        in.asInstanceOf[InputFieldScala[LocalDateTime]],
        new DateDistributionCalculationsScala(
          in.getOptions.asInstanceOf[OptionsScalaDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsScalaDate].getTimeIncrement,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )
  }
}
