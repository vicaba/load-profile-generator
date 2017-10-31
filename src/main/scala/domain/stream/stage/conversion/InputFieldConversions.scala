package domain.stream.stage.conversion

import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.stream.stage.source.{SourceValueDate, SourceValueNumber, SourceValueString}
import domain.transform.calculations.equal.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import domain.value.Value
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

object InputFieldConversions {

  def inputFieldToValueGenerator(in: InputField[_]): ValueGenerator[_, _] = in.getOptions match {
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

  def valueGeneratorToSource(vg: ValueGenerator[_, _]): Source[Value[_], NotUsed] = vg match {
    case value @ (_: StringValueGenerator) =>
      Source.fromGraph(new SourceValueString(value))
    case value @ (_: NumberValueGenerator) =>
      Source.fromGraph(new SourceValueNumber(value))
    case value @ (_: DateValueGenerator) =>
      Source.fromGraph(new SourceValueDate(value))
  }

}
