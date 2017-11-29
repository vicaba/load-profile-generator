package domain.transform.calculations.distribution;

import domain.in.field.options.NumberRange;
import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// TODO Distribution method chosen depends on the value in the InputField, right now it doesn't.

/**
 * Class used for calculating the next number with distributions, the distribution used depending on
 * the one chosen in the inputField.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class NumberDistributionCalculations implements DistributionCalculations<Float> {
  /** Constant that defines the name of the logger used for printing debug messages: {@value} */
  private static final String LOGGER_NAME = "distribution.logger";
  /**
   * Constant that defines the name of the logger used for printing information about what value of
   * counter we had when the distribution was applied : {@value}
   */
  private static final String TRACER_NAME = "distribution.trace";
  /** Constant with the degrees of freedom, necessary when using a distribution method: {@value} */
  private static final int DEGREES_OF_FREEDOM = 10;

  /** List of ranges, one of them will be chosen at random. */
  private ArrayList<NumberRange> numberRanges;
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
  /** Counter that keeps increasing for each number calculated, used for calculating the number. */
  private int counterNumber = 0;
  /** The distribution method used, chosen depending on the inputField. */
  private TDistribution tDistribution =
      new TDistribution(DEGREES_OF_FREEDOM); // We use the Student-T probability

  private Logger logger = LoggerFactory.getLogger(LOGGER_NAME);
  /**
   * Logger to be used to print info on a file to check at what values of the counter the
   * distribution triggers.
   */
  private Logger traceLogger = LoggerFactory.getLogger(TRACER_NAME);
  /**
   * The counter used in the distribution, that affects the probability that the distribution is
   * applied. The higher it is the bigger the number we will get to use as probability.
   */
  private int counterDistribution = 0;

  /**
   * Constructor.
   *
   * @param numberRanges ArrayList with all the ranges available to numberRanges.
   * @param offset The offset to be used for the distribution.
   * @param totalData The totalData to be used for the distribution.
   */
  public NumberDistributionCalculations(
      ArrayList<NumberRange> numberRanges, double offset, double totalData) {
    this.numberRanges = numberRanges;
    this.offset = offset;
    this.totalData = totalData;
    System.out.println("Offset is " + offset + " and totalData is " + totalData);
  }

  /**
   * Method that uses a distribution to calculate the probability to apply the distribution, and
   * then if that distribution is triggered then resets it back to normal.
   *
   * @return The next date, regardless if the distribution has been applied or not.
   */
  @Override
  public Float calculate() {
    NumberRange numberRange =
        this.numberRanges.get(0); // TODO Make it compatible with multiple ranges.
    /*
     * Applies a Student-T Continuous Distribution.
     * Values below 0 have low percentage. From -5 to -Inf, chance is close to 0%.
     * Values above 0 have high percentages. From +5 to +Int, chance is close to 100%.
     * The formula used is (count * (10/counterData) + initialValue).
     */
    // TODO Change this so we can easily swap the distribution method for another without affecting
    // anything.
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
      this.traceLogger.trace(this.counterDistribution + ",1|");
      logger.debug(counterDistribution + ",1|");
      System.out.println(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and distValue is "
              + distValue);
      this.resetCounter();
    } else {
      this.traceLogger.trace(this.counterDistribution + ",0|");
      logger.debug(counterDistribution + ",0|");
      System.out.println(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and distValue is "
              + distValue);
    }

    // TODO This makes no sense. NumberDistribution shouldn't work that way.

    /* The number is obtained by using the minimum and increasing it by the counter number. */
    float fTotal = numberRange.getMin() + this.counterNumber;
    float fMax = numberRange.getMax();

    /* We can't pass the max number allowed. If we do, we get the max number*/
    if (fTotal >= fMax) return fMax;
    this.counterNumber++;
    return fTotal;
  }

  /** Method that increases the distribution counter, used from the DistributionFlow. */
  @Override
  public void increaseDistributionCounter() {
    this.counterDistribution++;
  }

  /** Private method used to reset all counters to 0. */
  private void resetCounter() {
    this.counterNumber = 0;
    this.counterDistribution = 0;
  }
}
