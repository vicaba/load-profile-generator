package domain.stream.stage.flow.distribution

import akka.stream.stage._
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.in.distribution.InputDistribution
import domain.transform.calculations.Calculations
import domain.transform.distribution.DistributionsCheck
import domain.value.Value
import domain.value.generator.ValueGenerator
import org.apache.commons.math3.distribution.TDistribution
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._
import scala.util.Random

abstract class DistributionFlowT[V, T <: Calculations[V]](val dataGenerator: ValueGenerator[V, T],
                                                          val inputDistribution: List[InputDistribution])
  extends GraphStage[FlowShape[Value[V], Value[V]]] {

  val inlet: Inlet[Value[V]] = Inlet[Value[V]]("FB" + inputDistribution(0).getId + ".in")
  val outlet: Outlet[Value[V]] = Outlet[Value[V]]("FD" + inputDistribution(0).getResult.getId + ".out")

  val logger: Logger = LoggerFactory.getLogger("GraphLogger")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

    //private val distributionsCheck = new DistributionsCheck(inputDistribution.asJava)

    setHandler(inlet, new InHandler {
      override def onPush(): Unit = {
        //val input = grab(inlet)
        grab(inlet)

        //distributionsCheck.increaseCounter(input.getId)
        /*
        distributionsCheck.increaseAllCounters()
        if (distributionsCheck.checkDistribution()) {
          distributionsCheck.resetCounter()
          dataGenerator.reset()
        }
        */
        val data = dataGenerator.obtainNext()

        push(outlet, data)
        logger.debug("Throwing data with ID " + data.getId + " with type " + data.getType + " and value " + data.getValue)
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
