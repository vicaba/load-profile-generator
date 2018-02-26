package generator.infrastructure

import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.transform.calculations.equal.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

final class SourceGeneratorFactory {
  def createGeneratorFromInput(in: InputField[_]): ValueGenerator[_, _] = in.getOptions match {
    case _: OptionsString =>
      new StringValueGenerator(
        in,
        new StringEqualCalculations(in.getOptions.asInstanceOf[OptionsString].getAcceptedStrings))
    case _: OptionsNumber =>
      new NumberValueGenerator(
        in,
        new NumberEqualCalculations(in.getOptions.asInstanceOf[OptionsNumber].getRanges)
      )
    case _: OptionsDate =>
      new DateValueGenerator(
        in,
        new DateEqualCalculations(
          in.getOptions.asInstanceOf[OptionsDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsDate].getTimeIncrement)
      )
  }
}
