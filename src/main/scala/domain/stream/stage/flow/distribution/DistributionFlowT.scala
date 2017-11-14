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

    private val distributionsCheck = new DistributionsCheck(inputDistribution.asJava)
    private val tDistr = new TDistribution(10)  //We use the Student-T probability

    //TODO Next thing is to made the formula so the number put in the json indicates at which point we want the chance to be nearly impossible to miss is after receiving 7 values for example.
    /*
     * The initial value, that will go from -5 to -Inf.
     * If the initial value is -5, at half the counterData you have a 50% chance to apply the distribution.
     * The smaller the number is, the more data you need to pass so the percentage becomes higher.
     */
    private val initialValue = -20
    /*
     * The amount of data you need to pass so the chance to apply the distribution becomes close to 100%.
     * This works on a 10/counterData formula, so the smaller it is, the less it takes to reach 100%.
     * Keep in mind that counterData works on a [-5, 5] range, not on a [initialValue, 5] range.
     */
    private val counterData = 20.0
    private var count: Long = 0

    setHandler(inlet, new InHandler {
      override def onPush(): Unit = {
        //val input = grab(inlet)
        grab(inlet)
        /*
         * Applies a Student-T Continuous Distribution.
         * Values below 0 have low percentage. From -5 to -Inf, chance is close to 0%.
         * Values above 0 have high percentages. From +5 to +Int, chance is close to 100%.
         * The formula used is (count * (10/counterData) + initialValue).
         */
        var wtfishappening = (count * (10/counterData)) + initialValue
        logger.debug("WTF IS HAPPENING WITH COUNT "+ wtfishappening + ", MUL " + (10/counterData) + ", AND WTF " + wtfishappening)
        val distValue = tDistr.cumulativeProbability((count * (10/counterData)) + initialValue)
        /*
         * Once we have a random, we obtain a random using the random class.
         * The formula to consider if the distribution is applied is P(X <= distValue) = 1, P(X > distValue) = 0.
         * X being a random double value between 0 and 1.
         * As you can see in the formula, the bigger distValue is, the highest the chance that we apply distribution.
         */
        val random = new Random(1)
        if (random.nextDouble() <= distValue ) {
          logger.debug("After receiving " + count + " data, we succeeded at applying distribution with a probability of " + distValue)
          count = 0
        } else {
          count += 1
        }

        //distributionsCheck.increaseCounter(input.getId)
        distributionsCheck.increaseAllCounters()
        if (distributionsCheck.checkDistribution()) {
          distributionsCheck.resetCounter()
          dataGenerator.reset()
        }
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
