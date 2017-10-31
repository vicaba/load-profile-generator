package domain.stream.stage.conversion

import akka.NotUsed
import akka.stream.{FlowShape, Graph, SourceShape}
import akka.stream.scaladsl.{Flow, Source}
import domain.in.distribution.InputDistribution
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.stream.stage.flow.distribution.{DistributionFlowDate, DistributionFlowNumber, DistributionFlowString}
import domain.stream.stage.source.{SourceValueDate, SourceValueNumber, SourceValueString}
import domain.transform.calculations.distribution.DateDistributionCalculations
import domain.transform.calculations.equal.{NumberEqualCalculations, StringEqualCalculations}
import domain.value.Value
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

object InputDistributionConversions {

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
        new DateDistributionCalculations(
          in.getOptions.asInstanceOf[OptionsDate].getStartingDate,
          in.getOptions.asInstanceOf[OptionsDate].getTimeIncrement)
      )
  }

  def valueGeneratorToSource(vg: ValueGenerator[_, _]): Source[Value[_], NotUsed] = vg match {
    case value @ (_: StringValueGenerator) =>
      val g: Source[Value[String], NotUsed] = Source.fromGraph(new SourceValueString(value));g
    case value @ (_: NumberValueGenerator) =>
      Source.fromGraph(new SourceValueNumber(value))
    case value @ (_: DateValueGenerator) =>
      Source.fromGraph(new SourceValueDate(value))
      val a: Graph[SourceShape[Value[_]], NotUsed]  = new SourceValueDate(value)
  }

  def valueGeneratorToDistribution(vg: ValueGenerator[_, _],
                                   dist: InputDistribution): Flow[Value[_], Value[_], NotUsed] = vg match {
/*    case value : StringValueGenerator =>
       Flow.fromGraph(new DistributionFlowString(value, dist))
    case value: NumberValueGenerator =>
      Flow.fromGraph(new DistributionFlowNumber(value, dist))*/
    case value: DateValueGenerator =>
      Flow.fromGraph(new DistributionFlowDate(value, dist)).asInstanceOf[Flow[Value[_], Value[_], NotUsed]]
  }


}
