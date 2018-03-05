package domain.transform.calculations.distribution

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom

import org.apache.commons.math3.distribution.TDistribution

final class DateDistributionCalculationsScala(startingDate: String,
                                              timeIncrement: Int,
                                              offset: Double,
                                              totalData: Double) extends DistributionCalculationsScala[LocalDateTime] {
  private final val DateFormat = "dd-MM-yyyy HH:mm"
  private final val DegreesOfFreedom = 10
  private final val tDistribution = new TDistribution(this.DegreesOfFreedom)

  private var currentDate = LocalDateTime.parse(startingDate, DateTimeFormatter.ofPattern(this.DateFormat))
  private var counterDistribution = 0
  private var counterDate = 0


  override def calculate: LocalDateTime = {
    val distValue = this.tDistribution.cumulativeProbability(this.counterDistribution * (10 / this.totalData) + this.offset)

    val comparison = ThreadLocalRandom.current.nextInt(1, 100) * 0.01
    if (comparison <= distValue) {
      //this.traceLogger.trace(this.counterDistribution + ",1|")
      //this.logger.debug("Division is " + (10 / this.totalData) + ", operation is " + ((this.counterDistribution * (10 / this.totalData)) + this.offset) + ", and comparison<=distValue is " + comparison + "<=" + distValue + "\n")
      this.resetCounter()
    }
    else {
      //this.traceLogger.trace(this.counterDistribution + ",0|")
      //this.logger.debug("Division is " + (10 / this.totalData) + ", operation is " + ((this.counterDistribution * (10 / this.totalData)) + this.offset) + ", and comparison>distValue is " + comparison + ">" + distValue + "\n")
    }

    this.currentDate = this.currentDate.plusSeconds(this.timeIncrement * this.counterDate)
    this.counterDate += 1
    currentDate
  }

  override def increaseDistributionCounter(): Unit = {
    this.counterDistribution += 1
  }

  private def resetCounter(): Unit = {
    this.counterDate = 0
    this.counterDistribution = 0
  }
}
