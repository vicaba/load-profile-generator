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

    private var dataCounter = 1

    setHandler(inlet, new InHandler {
      override def onPush(): Unit = {
        //TODO Things that DistributionFlow must do, to think later.
        //TODO 1. Needs to increase dataCounter.
        //TODO 2. if dataCounter surpassed amount mentioned by inputDistribution, DataGenerator must apply the distribution rule
        //TODO 3. once rule is applied, data will be generated and passed through outlet.
        val data = dataGenerator.obtainNext()
        push(outlet, data)
      }
    })
  }

  override def shape: FlowShape[Value[V], Value[V]] = FlowShape(inlet, outlet)
}
