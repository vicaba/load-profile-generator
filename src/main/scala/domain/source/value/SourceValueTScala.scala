package domain.source.value

import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import domain.transform.calculations.CalculationsScala
import domain.value.ValueScala
import domain.value.generator.ValueGeneratorScala

/**
  * Abstract class that defines the Source responsible of generating values.
  *
  * @param dataGenerator The data generator of this flow.
  * @tparam V Type of the value that arrives to this flow.
  *           Allows different types, which are specified by the children of this node.
  * @tparam T Type of the Calculations used by this node, which also depends on the type of value it receives.
  */
abstract class SourceValueTScala[V, T <: CalculationsScala[V]](val dataGenerator: ValueGeneratorScala[V, T])
  extends GraphStage[SourceShape[ValueScala[V]]] {

  val output: Outlet[ValueScala[V]] =
    Outlet[ValueScala[V]](s"${dataGenerator.getId}")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          val data = dataGenerator.obtainNext
          push(output, data)
        }
      })
    }

  override def shape: SourceShape[ValueScala[V]] = SourceShape(output)

}
