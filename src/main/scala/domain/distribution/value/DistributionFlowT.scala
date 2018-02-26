package domain.distribution.value

import akka.stream.stage._
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.transform.distribution.DistributionsCheck
import domain.value.Value
import domain.value.generator.ValueGenerator
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
  * Abstract class that defines the Flow responsible of applying stream.distribution before generating its value,
  * using what is sent from the connected broadcasts to increase the chance of the stream.distribution to apply.
  * Then it will create the next value and sent it to the zipper that is connected with.
  *
  * @param dataGenerator     The data generator of this flow.
  * @param inputDistribution The info necessary to use stream.distribution in this node.
  * @tparam V Type of the value that arrives to this flow.
  *           Allows different types, which are specified by the children of this node.
  * @tparam T Type of the Calculations used by this node, which also depends on the type of value it receives.
  */
abstract class DistributionFlowT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T],
                                                          val inputDistribution: List[InputDistribution])
  extends GraphStage[FlowShape[Value[V], Value[V]]] {
  val inlet: Inlet[Value[V]] = Inlet[Value[V]]("FB" + inputDistribution(0).getId + ".in")
  val outlet: Outlet[Value[V]] = Outlet[Value[V]]("FD" + inputDistribution(0).getResult.getId + ".out")
  private val logger = LoggerFactory.getLogger("stream.distribution.logger")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    private val distributionsCheck = new DistributionsCheck(inputDistribution.asJava)

    setHandler(inlet, new InHandler {

      override def onPush(): Unit = {
        //val input = grab(inlet)
        grab(inlet)

        //TODO Need more methods for stream.distribution.
        distributionsCheck.increaseAllCounters()
        if (distributionsCheck.checkDistribution()) {
          distributionsCheck.resetCounter()
          dataGenerator.increaseCounter()
        }

        val data = dataGenerator.obtainNext()
        logger.debug("Data with ID " + data.getId + " has value " + data.getValue + "\n")
        push(outlet, data)
      }
    })

    setHandler(outlet, new OutHandler {
      override def onPull(): Unit = {
        pull(inlet)
      }
    })
  }

  override def shape: FlowShape[Value[V], Value[V]] = FlowShape(inlet, outlet)
}
