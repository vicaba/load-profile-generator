package domain.distribution.generator

import java.time.LocalDateTime

import domain.in.field.InputFieldScala
import domain.in.field.options._
import domain.transform.calculations.ApplianceCalculationsScala
import domain.transform.calculations.distribution.{DateDistributionCalculationsScala, NumberDistributionCalculationsScala}
import domain.transform.calculations.equal.StringEqualCalculationsScala
import domain.value.generator._

object DistributionGeneratorFactoryScala {

  def createGeneratorFromInput(in: InputFieldScala): ValueGeneratorScala[_, _] = in.options match {
    case _: OptionsScalaString =>
      new StringValueGeneratorScala(
        in,
        new StringEqualCalculationsScala(in.options.asInstanceOf[OptionsScalaString].acceptedStrings))

    case _: OptionsScalaNumber =>
      new NumberValueGeneratorScala(
        in,
        new NumberDistributionCalculationsScala(
          in.options.asInstanceOf[OptionsScalaNumber].ranges,
          in.distributionInfo.offset,
          in.distributionInfo.totalData
        )
      )

    case _: OptionsScalaDate =>
      new DateValueGeneratorScala(
        in,
        new DateDistributionCalculationsScala(
          in.options.asInstanceOf[OptionsScalaDate].startingDate,
          in.options.asInstanceOf[OptionsScalaDate].timeIncrement,
          in.distributionInfo.offset,
          in.distributionInfo.totalData
        )
      )

  }
}
