package domain.stream.stage.source

import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import domain.transform.calculations.Calculations
import domain.value.Value
import domain.value.generator.ValueGenerator

abstract class SourceValueT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T])
  extends GraphStage[SourceShape[Value[V]]] {

  // Outlet identifier is the inputField.id
  val output: Outlet[Value[V]] =
    Outlet[Value[V]](s"${dataGenerator.getId}")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(output, new OutHandler {
        override def onPull(): Unit = push(output, dataGenerator.obtainNext())
      })
    }

  override def shape: SourceShape[Value[V]] = SourceShape(output)

}
