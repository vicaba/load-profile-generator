package domain.transform.calculations.distribution;

import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class DateDistributionCalculations implements DistributionCalculations<LocalDateTime> {
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String LOGGER_NAME = "distribution.logger";
  private static final String TRACER_NAME = "distribution.trace";
  private static final int DEGREES_OF_FREEDOM = 10;

  /** The starting date, used if we need to reset the distribution back to initial values. */
  private LocalDateTime startingDate;
  /** The amount of time in seconds that pass for each calculated date. */
  private int timeIncrement;
  /**
   * The initial value, that will go from -5 to -Inf. If the initial value is -5, at half the
   * counterData you have a 50% chance to apply the distribution. The smaller the number is, the
   * more data you need to pass so the percentage becomes higher.
   */
  private double offset;
  /**
   * The amount of data you need to pass so the chance to apply the distribution becomes close to
   * 100%. This works on a 10/counterData formula, so the smaller it is, the less it takes to reach
   * 100%. Keep in mind that counterData works on a [-5, 5] range, not on a [initialValue, 5] range.
   */
  private double totalData;
  /** Counter that keeps increasing for each new date we have to calculate. */
  private int counterDate = 0;
  /**
   * The counter used in the distribution, that affects the probability that the distribution is
   * applied.
   */
  private double counterDistribution = 0;

  // TODO Change this so we can easily swap the distribution method for another without affecting
  // anything.
  private TDistribution tDistribution =
      new TDistribution(DEGREES_OF_FREEDOM); // We use the Student-T probability
  /** Logger to be used to write debug messages */
  private Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
  /**
   * Logger to be used to print info on a file to check at what values of the counter the
   * distribution triggers.
   */
  private Logger traceLogger = LoggerFactory.getLogger(TRACER_NAME);

  public DateDistributionCalculations(
      String startingDate, int timeIncrement, double offset, double totalData) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
    this.offset = offset;
    this.totalData = totalData;
  }

  @Override
  public LocalDateTime calculate() {
    double distValue =
        tDistribution.cumulativeProbability(counterDistribution * (10 / totalData) + offset);

    /*
     * Once we have a random, we obtain a random using the random class.
     * The formula to consider if the distribution is applied is P(X <= distValue) = 1, P(X > distValue) = 0.
     * X being a random double value between 0 and 1.
     * As you can see in the formula, the bigger distValue is, the highest the chance that we apply distribution.
     */
    double comparison = ThreadLocalRandom.current().nextInt(1, 100) * 0.01;
    if (comparison <= distValue) {
      traceLogger.trace(counterDistribution + ",1|");
      logger.debug(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and comparison<=distValue is "
              + comparison
              + "<="
              + distValue
              + "\n");
      this.resetCounter();
    } else {
      traceLogger.trace(counterDistribution + ",0|");
      logger.debug(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and comparison>distValue is "
              + comparison
              + ">"
              + distValue
              + "\n");
    }

    LocalDateTime currentDate =
        this.startingDate.plusSeconds(this.timeIncrement * this.counterDate);
    this.counterDate++;
    return currentDate;
  }

  @Override
  public void increaseDistributionCounter() {
    this.counterDistribution++;
  }

  private void resetCounter() {
    this.counterDate = 0;
    this.counterDistribution = 0;
  }
}
