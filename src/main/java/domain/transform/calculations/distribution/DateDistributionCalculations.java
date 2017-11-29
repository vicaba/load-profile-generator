package domain.transform.calculations.distribution;

import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

// TODO Distribution method chosen depends on the value in the InputField, right now it doesn't.

/**
 * Class used for calculating the next date with distributions, the distribution used depending on
 * the one chosen in the inputField.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class DateDistributionCalculations implements DistributionCalculations<LocalDateTime> {
  /** Constant that defines the format the date will have: {@value} */
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
  /** Constant that defines the name of the logger used for printing debug messages: {@value} */
  private static final String LOGGER_NAME = "distribution.logger";
  /**
   * Constant that defines the name of the logger used for printing information about what value of
   * counter we had when the distribution was applied : {@value}
   */
  private static final String TRACER_NAME = "distribution.trace";
  /** Constant with the degrees of freedom, necessary when using a distribution method: {@value} */
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
   * applied. The higher it is the bigger the number we will get to use as probability.
   */
  private double counterDistribution = 0;

  // TODO Change this so we can easily swap the distribution method for another without affecting anything.
  private TDistribution tDistribution =
      new TDistribution(DEGREES_OF_FREEDOM); // We use the Student-T probability
  /** Logger to be used to write debug messages */
  private Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
  /**
   * Logger to be used to print info on a file to check at what values of the counter the
   * distribution triggers.
   */
  private Logger traceLogger = LoggerFactory.getLogger(TRACER_NAME);

  /**
   * Constructor.
   *
   * @param startingDate The starting date to generate date values.
   * @param timeIncrement The distance between dates in seconds.
   * @param offset The offset to be used for the distribution.
   * @param totalData The totalData to be used for the distribution.
   */
  public DateDistributionCalculations(
      String startingDate, int timeIncrement, double offset, double totalData) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
    this.offset = offset;
    this.totalData = totalData;
  }

  /**
   * Method that uses a distribution to calculate the probability to apply the distribution, and
   * then if that distribution is triggered then resets it back to normal.
   *
   * @return The next date, regardless if the distribution has been applied or not.
   */
  @Override
  public LocalDateTime calculate() {
    double distValue =
        this.tDistribution.cumulativeProbability(
            this.counterDistribution * (10 / this.totalData) + this.offset);

    /*
     * Once we have a random, we obtain a random using the random class.
     * The formula to consider if the distribution is applied is P(X <= distValue) = 1, P(X > distValue) = 0.
     * X being a random double value between 0 and 1.
     * As you can see in the formula, the bigger distValue is, the highest the chance that we apply distribution.
     */
    double comparison = ThreadLocalRandom.current().nextInt(1, 100) * 0.01;
    if (comparison <= distValue) {
      this.traceLogger.trace(this.counterDistribution + ",1|");
      this.logger.debug(
          "Division is "
              + (10 / this.totalData)
              + ", operation is "
              + ((this.counterDistribution * (10 / this.totalData)) + this.offset)
              + ", and comparison<=distValue is "
              + comparison
              + "<="
              + distValue
              + "\n");
      this.resetCounter();
    } else {
      this.traceLogger.trace(this.counterDistribution + ",0|");
      this.logger.debug(
          "Division is "
              + (10 / this.totalData)
              + ", operation is "
              + ((this.counterDistribution * (10 / this.totalData)) + this.offset)
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

  /** Method that increases the distribution counter, used from the DistributionFlow. */
  @Override
  public void increaseDistributionCounter() {
    this.counterDistribution++;
  }

  /** Private method used to reset all counters to 0. */
  private void resetCounter() {
    this.counterDate = 0;
    this.counterDistribution = 0;
  }
}
