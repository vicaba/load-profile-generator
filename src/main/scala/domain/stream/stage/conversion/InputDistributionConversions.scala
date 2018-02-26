package domain.stream.stage.conversion

import akka.NotUsed
import akka.stream.scaladsl.Flow
import domain.in.distribution.InputDistribution
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.transform.calculations.distribution.{DateDistributionCalculations, NumberDistributionCalculations}
import domain.transform.calculations.equal.StringEqualCalculations
import domain.value.Value
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}
import stream.distribution.infrastructure.value.{DistributionFlowDate, DistributionFlowNumber}

object InputDistributionConversions {

  def inputFieldToValueGenerator(in: InputField[_]): ValueGenerator[_, _] = in.getOptions match {
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


  def valueGeneratorToDistribution(vg: ValueGenerator[_, _],
                                   dist: List[InputDistribution]): Flow[Value[_], Value[_], NotUsed] = vg match {
    /*    case value : StringValueGenerator =>
           Flow.fromGraph(new DistributionFlowString(value, dist))
     */
    case value: NumberValueGenerator =>
      Flow.fromGraph(new DistributionFlowNumber(value, dist)).asInstanceOf[Flow[Value[_], Value[_], NotUsed]]
    case value: DateValueGenerator =>
      Flow.fromGraph(new DistributionFlowDate(value, dist)).asInstanceOf[Flow[Value[_], Value[_], NotUsed]]
  }
}
