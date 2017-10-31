package domain.stream.stage.flow.distribution

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.transform.distribution.DistributionsCheck
import domain.value.Value
import infrastructure.value.preparation.ValueGenerator

abstract class DistributionFlowT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T],
                                                 val inputDistribution: InputDistribution)
  extends GraphStage[FlowShape[Value[V], Value[V]]] {

  val inlet: Inlet[Value[V]] = Inlet[Value[V]]("Broadcast" + inputDistribution.getId + ".in")
  val outlet: Outlet[Value[V]] = Outlet[Value[V]]("Distribution" + inputDistribution.getResult.getId + ".out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

    private var dataCounter = 0

    setHandler(inlet, new InHandler {
      override def onPush(): Unit = {
        dataCounter += 1
        val distcheck = new DistributionsCheck(inputDistribution)
        if (distcheck.checkDistribution(dataCounter)) {
          dataCounter = 0
          //dataGenerator.resetGenerator()
        }
        val data = dataGenerator.obtainNext()
        push(outlet, data)
      }
    })
  }

  override def shape: FlowShape[Value[V], Value[V]] = FlowShape(inlet, outlet)
}
