package domain.distribution.generator

import java.time.LocalDateTime

import domain.in.field.InputFieldScala
import domain.in.field.options._
import domain.transform.calculations.ApplianceCalculationsScala
import domain.transform.calculations.distribution.{DateDistributionCalculationsScala, NumberDistributionCalculationsScala}
import domain.transform.calculations.equal.StringEqualCalculationsScala
import domain.value.generator._

object DistributionGeneratorFactoryScala {

  def createGeneratorFromInput(in: InputFieldScala): ValueGeneratorScala[_, _] = in.getOptions match {
    case _: OptionsScalaString =>
      new StringValueGeneratorScala(
        in,
        new StringEqualCalculationsScala(in.getOptions.asInstanceOf[OptionsScalaString].getAcceptedStrings))

    case _: OptionsScalaNumber =>
      new NumberValueGeneratorScala(
        in,
        new NumberDistributionCalculationsScala(
          in.getOptions.asInstanceOf[OptionsScalaNumber].getRanges,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )

    case _: OptionsScalaDate =>
      new DateValueGeneratorScala(
        in,
        new DateDistributionCalculationsScala(
          in.getOptions.asInstanceOf[OptionsScalaDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsScalaDate].getTimeIncrement,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )

  }
}
