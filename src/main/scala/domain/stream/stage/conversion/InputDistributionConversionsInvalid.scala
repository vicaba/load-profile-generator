package domain.stream.stage.conversion


import akka.NotUsed
import akka.stream.scaladsl.Flow
import distribution.flow.infrastructure.{DistributionFlowDate, DistributionFlowNumber, DistributionFlowString}
import domain.in.distribution.InputDistribution
import domain.in.field.InputField
import domain.value.Value
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}


object InputDistributionConversionsInvalid {


  def inputFieldToValueGenerator(in: InputField[_]): ValueGenerator[_, _] =
    InputFieldConversions.inputFieldToValueGenerator(in)


  def valueGeneratorToDistribution(vg: ValueGenerator[_, _],
                                   dist: List[InputDistribution]): Flow[Value[_], Value[_], NotUsed] = {

    def eraseType[A, B, C](f: Flow[Value[A], Value[B], C]) = f.asInstanceOf[Flow[Value[_], Value[_], C]]

    vg match {
      case value: StringValueGenerator =>
        eraseType(Flow.fromGraph(new DistributionFlowString(value, dist)))
      case value: NumberValueGenerator =>
        eraseType(Flow.fromGraph(new DistributionFlowNumber(value, dist)))
      case value: DateValueGenerator =>
        eraseType(Flow.fromGraph(new DistributionFlowDate(value, dist)))
    }


  }


}
