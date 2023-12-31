package domain.test.stage.conversion

import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.transform.calculations.equal.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import domain.value.Value
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}
import domain.source.value.{SourceValueDate, SourceValueNumber, SourceValueString}

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
    case value@(_: StringValueGenerator) =>
      val g: Source[Value[String], NotUsed] = Source.fromGraph(new SourceValueString(value)); g
    case value@(_: NumberValueGenerator) =>
      Source.fromGraph(new SourceValueNumber(value))
    case value@(_: DateValueGenerator) =>
      Source.fromGraph(new SourceValueDate(value))
  }

}
