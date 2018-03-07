package domain.transform.calculations.distribution

import java.util.concurrent.ThreadLocalRandom

import domain.in.field.options.NumberRangeScala
import org.apache.commons.math3.distribution.TDistribution

final class NumberDistributionCalculationsScala(numberRanges: Seq[NumberRangeScala],
                                                offset: Double,
                                                totalData: Double) extends DistributionCalculationsScala[Double] {

  private final val DegreesOfFreedom = 10
  private final val tDistribution = new TDistribution(this.DegreesOfFreedom)

  private var counterDistribution = 0
  private var counterNumber = 0

  override def calculate: Double = {
    val numberRange = this.numberRanges(0)
    // TODO Make it compatible with multiple ranges.

    // TODO Change so we can easily swap the stream.distribution method for another without affecting anything.
    val distValue = tDistribution.cumulativeProbability(this.counterDistribution * (10 / totalData) + offset)

    val comparison = ThreadLocalRandom.current.nextInt(1, 100) * 0.01
    if (comparison <= distValue) {
      //this.traceLogger.trace(this.counterDistribution + ",1|")
      //logger.debug(counterDistribution + ",1|")
      //System.out.println("Division is " + (10 / totalData) + ", operation is " + ((counterDistribution * (10 / totalData)) + offset) + ", and distValue is " + distValue)
      this.resetCounter()
    }
    else {
      //this.traceLogger.trace(this.counterDistribution + ",0|")
      //logger.debug(counterDistribution + ",0|")
      //System.out.println("Division is " + (10 / totalData) + ", operation is " + ((counterDistribution * (10 / totalData)) + offset) + ", and distValue is " + distValue)
    }

    // TODO This makes no sense. NumberDistribution shouldn't work that way.

    /* The number is obtained by using the minimum and increasing it by the counter number. */
    val fTotal = numberRange.min + this.counterNumber
    val fMax = numberRange.max

    /* We can't pass the max number allowed. If we do, we get the max number*/
    if (fTotal >= fMax) return fMax
    this.counterNumber += 1
    fTotal
  }

  override def increaseDistributionCounter(): Unit = {
    this.counterDistribution += 1
  }

  private def resetCounter(): Unit = {
    this.counterNumber = 0
    this.counterDistribution = 0
  }
}
