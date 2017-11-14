package domain.transform.calculations.distribution;

import domain.in.field.options.NumberRange;
import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Class responsible of giving a valid number field depending on the input configurations that are
 * passed.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class NumberDistributionCalculations implements DistributionCalculations<Float> {
  /** List of ranges, one of them will be chosen at random. */
  private ArrayList<NumberRange> numberRanges;

  private int counterNumber = 0;
  private TDistribution tDistribution = new TDistribution(10); // We use the Student-T probability
  private Logger logger = LoggerFactory.getLogger("CalculationsLogger");
  private SecureRandom random = new SecureRandom();

  // TODO Next thing is to made the formula so the number put in the json indicates at which point
  // we want the chance to be nearly impossible to miss is after receiving 7 values for example.
  /*
   * The initial value, that will go from -5 to -Inf.
   * If the initial value is -5, at half the counterData you have a 50% chance to apply the distribution.
   * The smaller the number is, the more data you need to pass so the percentage becomes higher.
   */
  private double initialValue = -20;
  /*
   * The amount of data you need to pass so the chance to apply the distribution becomes close to 100%.
   * This works on a 10/counterData formula, so the smaller it is, the less it takes to reach 100%.
   * Keep in mind that counterData works on a [-5, 5] range, not on a [initialValue, 5] range.
   */
  private double counterData = 20.0;

  public NumberDistributionCalculations(ArrayList<NumberRange> numberRanges) {
    this.numberRanges = numberRanges;
  }

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
    double distValue =
        tDistribution.cumulativeProbability(counterNumber * (10 / counterData) + initialValue);
    logger.debug(
        "Division is "
            + (10 / counterData)
            + ", operation is "
            + ((counterNumber * (10 / counterData)) + initialValue)
            + ", and distValue is "
            + distValue);

    //TODO After testing, this method doesn't work, as the values always seem to be the same ever after a lot of time passes. I need to find a way to define a P(X <= distValue)
    /*
     * Once we have a random, we obtain a random using the random class.
     * The formula to consider if the distribution is applied is P(X <= distValue) = 1, P(X > distValue) = 0.
     * X being a random double value between 0 and 1.
     * As you can see in the formula, the bigger distValue is, the highest the chance that we apply distribution.
     */
    if (random.nextDouble() <= distValue) {
      logger.debug(
          "After receiving "
              + counterNumber
              + " data, we succeeded at applying distribution with a probability of "
              + distValue);
      this.resetCounter();
    }

    float fTotal = numberRange.getMin() + this.counterNumber;
    float fMax = numberRange.getMax();
    // return ThreadLocalRandom.current().nextFloat() * (numberRange.getMax() - fMin) + fMin;
    if (fTotal >= fMax) return fMax;
    this.counterNumber++;
    return fTotal;
  }

  @Override
  public void resetCounter() {
    this.counterNumber = 0;
  }
}
