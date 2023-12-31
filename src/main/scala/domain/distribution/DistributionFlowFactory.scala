package domain.distribution

import akka.NotUsed
import akka.stream.scaladsl.Flow
import domain.distribution.value.{DistributionFlowDate, DistributionFlowNumber}
import domain.in.distribution.InputDistribution
import domain.value.Value
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, ValueGenerator}

object DistributionFlowFactory {
  def createFlowFromGenerator(vg: ValueGenerator[_, _],
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
