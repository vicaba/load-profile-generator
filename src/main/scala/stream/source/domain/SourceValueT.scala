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

  /** The outlet of the Source. */
  val output: Outlet[Value[V]] =
    Outlet[Value[V]](s"${dataGenerator.getId}")

  /**
    * The logic behind the Source. It will generate the next value and send it through the outlet.
    *
    * @param inheritedAttributes Not Used.
    * @return Not Used.
    */
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      /**
        * Handler that triggers if data is pulled from the outlet.
        * We will generate the next value and proceed to push it through the outlet.
        */
      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          val data = dataGenerator.obtainNext()
          push(output, data)
        }
      })
    }

  /**
    * This method is needed to define the SourceShape for this Source using the outlet we defined.
    *
    * @return Returns a SourceShape that uses the outlet defined as the outlet of this Source.
    */
  override def shape: SourceShape[Value[V]] = SourceShape(output)

}
