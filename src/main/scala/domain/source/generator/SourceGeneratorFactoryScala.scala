package domain.source.generator

import domain.in.field.InputFieldScala
import domain.in.field.options._
import domain.transform.calculations.ApplianceCalculationsScala
import domain.transform.calculations.equal.{DateEqualCalculationsScala, NumberEqualCalculationsScala, StringEqualCalculationsScala}
import domain.value.generator._

object SourceGeneratorFactoryScala {
  def createGeneratorFromInput(in: InputFieldScala): ValueGeneratorScala[_, _] = in.options match {
    case _: OptionsScalaString =>
      new StringValueGeneratorScala(
        in.asInstanceOf[InputFieldScala],
        new StringEqualCalculationsScala(in.options.asInstanceOf[OptionsScalaString].acceptedStrings))

    case _: OptionsScalaNumber =>
      new NumberValueGeneratorScala(
        in.asInstanceOf[InputFieldScala],
        new NumberEqualCalculationsScala(in.options.asInstanceOf[OptionsScalaNumber].ranges)
      )

    case _: OptionsScalaDate =>
      new DateValueGeneratorScala(
        in.asInstanceOf[InputFieldScala],
        new DateEqualCalculationsScala(
          in.options.asInstanceOf[OptionsScalaDate].startingDate,
          in.options.asInstanceOf[OptionsScalaDate].timeIncrement)
      )

    case _: OptionsScalaAppliance =>
      new ApplianceValueGeneratorScala(
        in,
        new ApplianceCalculationsScala(
          in.options.asInstanceOf[OptionsScalaAppliance].appliance
        )
      )
  }
}
