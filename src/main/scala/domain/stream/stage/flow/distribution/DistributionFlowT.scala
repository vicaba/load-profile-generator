package domain.stream.stage.flow.distribution

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.value.Value
import infrastructure.value.preparation.ValueGenerator

abstract class DistributionFlowT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T],
                                                 val inputDistribution: InputDistribution)
  extends GraphStage[FlowShape[Value[V], Value[V]]] {

  val inlet: Inlet[Value[V]] = Inlet[Value[V]]("Broadcast" + inputDistribution.getId + ".in")
  val outlet: Outlet[Value[V]] = Outlet[Value[V]]("Distribution" + inputDistribution.getResult.getId + ".out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

    setHandler(inlet, new InHandler {
      override def onPush(): Unit = {
        val data = dataGenerator.obtainNext()
        //TODO Apply rule with inputDistr and data
        push(outlet, data)
      }
    })
  }

  override def shape: FlowShape[Value[V], Value[V]] = FlowShape(inlet, outlet)
}
