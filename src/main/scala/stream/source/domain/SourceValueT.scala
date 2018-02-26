package stream.source.domain

import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import domain.transform.calculations.Calculations
import domain.value.Value
import domain.value.generator.ValueGenerator

/**
  * Abstract class that defines the Source responsible of generating values.
  *
  * @param dataGenerator The data generator of this flow.
  * @tparam V Type of the value that arrives to this flow.
  *           Allows different types, which are specified by the children of this node.
  * @tparam T Type of the Calculations used by this node, which also depends on the type of value it receives.
  */
abstract class SourceValueT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T])
  extends GraphStage[SourceShape[Value[V]]] {

  val output: Outlet[Value[V]] =
    Outlet[Value[V]](s"${dataGenerator.getId}")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          val data = dataGenerator.obtainNext()
          push(output, data)
        }
      })
    }

  override def shape: SourceShape[Value[V]] = SourceShape(output)

}
