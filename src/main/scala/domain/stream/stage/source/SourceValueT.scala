package domain.stream.stage.source

import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import domain.transform.calculations.Calculations
import domain.value.Value
import infrastructure.value.preparation.ValueGenerator

abstract class SourceValueT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T])
    extends GraphStage[SourceShape[Value[V]]] {

  val output: Outlet[Value[V]] =
    Outlet[Value[V]](s"Source${dataGenerator.getName}${dataGenerator.getId}.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(output, new OutHandler {
        override def onPull(): Unit = push(output, dataGenerator.obtainNext())
      })
    }

  override def shape: SourceShape[Value[V]] = SourceShape(output)

}
