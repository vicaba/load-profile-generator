package domain.distribution.value

import akka.stream.stage._
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.in.distribution.InputDistributionScala
import domain.transform.calculations.CalculationsScala
import domain.transform.distributions.DistributionsCheckScala
import domain.value.ValueScala
import domain.value.generator.ValueGeneratorScala
import org.slf4j.LoggerFactory

abstract class DistributionFlowTScala[V, T <: CalculationsScala[V]](val dataGenerator: ValueGeneratorScala[V, T],
                                                                    val inputDistribution: Seq[InputDistributionScala])
  extends GraphStage[FlowShape[ValueScala[V], ValueScala[V]]] {
  val inlet: Inlet[ValueScala[V]] = Inlet[ValueScala[V]]("FB" + inputDistribution(0).getId + ".in")
  val outlet: Outlet[ValueScala[V]] = Outlet[ValueScala[V]]("FD" + inputDistribution(0).getResult.getId + ".out")
  private val logger = LoggerFactory.getLogger("stream.distribution.logger")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    private val distributionsCheck = new DistributionsCheckScala(inputDistribution)

    setHandler(inlet, new InHandler {

      override def onPush(): Unit = {
        //val input = grab(inlet)
        grab(inlet)

        //TODO Need more methods for stream.distribution.
        distributionsCheck.increaseAllCounters()
        if (distributionsCheck.checkDistribution) {
          distributionsCheck.resetCounter()
          dataGenerator.increaseCounter()
        }

        val data = dataGenerator.obtainNext
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

  override def shape: FlowShape[ValueScala[V], ValueScala[V]] = FlowShape(inlet, outlet)
}
