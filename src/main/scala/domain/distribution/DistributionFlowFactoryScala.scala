package domain.distribution

import akka.NotUsed
import akka.stream.scaladsl.Flow
import domain.distribution.value.{DistributionFlowDateScala, DistributionFlowNumberScala, DistributionFlowStringScala}
import domain.in.distribution.InputDistributionScala
import domain.value.ValueScala
import domain.value.generator._

object DistributionFlowFactoryScala {
  def createFlowFromGenerator(vg: ValueGeneratorScala[_, _],
                              dist: Seq[InputDistributionScala]): Flow[ValueScala[_], ValueScala[_], NotUsed] = vg match {
    /*case value: StringValueGeneratorScala =>
      Flow.fromGraph(new DistributionFlowStringScala(value, dist)).asInstanceOf[Flow[ValueScala[_], ValueScala[_], NotUsed]]*/
    case value: NumberValueGeneratorScala =>
      Flow.fromGraph(new DistributionFlowNumberScala(value, dist)).asInstanceOf[Flow[ValueScala[_], ValueScala[_], NotUsed]]
    case value: DateValueGeneratorScala =>
      Flow.fromGraph(new DistributionFlowDateScala(value, dist)).asInstanceOf[Flow[ValueScala[_], ValueScala[_], NotUsed]]
  }
}
