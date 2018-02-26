package generator.infrastructure

import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.transform.calculations.distribution.{DateDistributionCalculations, NumberDistributionCalculations}
import domain.transform.calculations.equal.StringEqualCalculations
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

final class DistributionGeneratorFactory {

  def createGeneratorFromInput(in: InputField[_]): ValueGenerator[_, _] = in.getOptions match {
    case _: OptionsString =>
      new StringValueGenerator(
        in,
        new StringEqualCalculations(in.getOptions.asInstanceOf[OptionsString].getAcceptedStrings))
    case _: OptionsNumber =>
      new NumberValueGenerator(
        in,
        new NumberDistributionCalculations(
          in.getOptions.asInstanceOf[OptionsNumber].getRanges,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )
    case _: OptionsDate =>
      new DateValueGenerator(
        in,
        new DateDistributionCalculations(
          in.getOptions.asInstanceOf[OptionsDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsDate].getTimeIncrement,
          in.getDistributionInfo.getOffset,
          in.getDistributionInfo.getTotalData
        )
      )
  }
}
