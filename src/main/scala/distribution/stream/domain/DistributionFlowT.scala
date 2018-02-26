package distribution.stream.domain

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
  * Abstract class that defines the Flow responsible of applying distribution before generating its value,
  * using what is sent from the connected broadcasts to increase the chance of the distribution to apply.
  * Then it will create the next value and sent it to the zipper that is connected with.
  *
  * @param dataGenerator     The data generator of this flow.
  * @param inputDistribution The info necessary to use distribution in this node.
  * @tparam V Type of the value that arrives to this flow.
  *           Allows different types, which are specified by the children of this node.
  * @tparam T Type of the Calculations used by this node, which also depends on the type of value it receives.
  */
abstract class DistributionFlowT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T],
                                                          val inputDistribution: List[InputDistribution])
  extends GraphStage[FlowShape[Value[V], Value[V]]] {
  /** The inlet of this flow. Only compatible with one inlet for connections. */
  val inlet: Inlet[Value[V]] = Inlet[Value[V]]("FB" + inputDistribution(0).getId + ".in")
  /** The outlet of this flow. Only compatible with one outlet for connections. */
  val outlet: Outlet[Value[V]] = Outlet[Value[V]]("FD" + inputDistribution(0).getResult.getId + ".out")
  /** The logger we use to check for debug purposes. */
  private val logger = LoggerFactory.getLogger("distribution.logger")

  /**
    * The logic behind the Flow. It will grab the data from the inlet, affect the distribution chance with it,
    * see if distribution will apply this time (and apply if it does) and generates the next data,
    * to at last pass it through its outlet.
    *
    * @param inheritedAttributes Not Used.
    * @return Not Used.
    */
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    /** The class we use to check if we need to influence the distribution chance. */
    private val distributionsCheck = new DistributionsCheck(inputDistribution.asJava)

    /**
      * The handler we put to the sink. We only use onPush for this sink.
      */
    setHandler(inlet, new InHandler {
      /**
        * Handler that triggers if data arrives through the inlet.
        * We will grab it, check (and apply if necessary) distribution,
        * generate the next value, and send it through its outlet.
        */
      override def onPush(): Unit = {
        //val input = grab(inlet)
        grab(inlet)

        //TODO Need more methods for distribution.
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

    /**
      * Handler that triggers if data is grabbed from the outlet.
      * We need to do a pull of input in that case so the flow continues,
      * else our graph will become stuck.
      */
    setHandler(outlet, new OutHandler {
      override def onPull(): Unit = {
        pull(inlet)
      }
    })
  }

  /**
    * This method is needed to define the FlowShape for this flow using the inlet and outlet we defined.
    *
    * @return Returns a FlowShape that uses the inlet and outlet defined as the inlet and outlet of this Flow.
    */
  override def shape: FlowShape[Value[V], Value[V]] = FlowShape(inlet, outlet)
}
