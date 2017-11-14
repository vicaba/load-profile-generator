package domain.transform.calculations.distribution;

import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class DateDistributionCalculations implements DistributionCalculations<LocalDateTime> {
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  private LocalDateTime startingDate;
  private int timeIncrement;
  private int counterDate = 0;

  private TDistribution tDistribution = new TDistribution(10); // We use the Student-T probability
  private Logger logger = LoggerFactory.getLogger("CalculationsLogger");

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
  private double counterData = 30.0;

  public DateDistributionCalculations(String startingDate, int timeIncrement) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
  }

  @Override
  public LocalDateTime calculate() {
    double distValue =
        tDistribution.cumulativeProbability(counterDate * (10 / counterData) + initialValue);
    logger.debug(
        "Division is "
            + (10 / counterData)
            + ", operation is "
            + ((counterDate * (10 / counterData)) + initialValue)
            + ", and distValue is "
            + distValue);

    //TODO After testing, this method doesn't work, as the values always seem to be the same ever after a lot of time passes. I need to find a way to define a P(X <= distValue)
    /*
     * Once we have a random, we obtain a random using the random class.
     * The formula to consider if the distribution is applied is P(X <= distValue) = 1, P(X > distValue) = 0.
     * X being a random double value between 0 and 1.
     * As you can see in the formula, the bigger distValue is, the highest the chance that we apply distribution.
     */
    double comparison = ThreadLocalRandom.current().nextDouble(1);
    if (comparison <= distValue) {
      logger.debug(
          "After receiving "
              + counterDate
              + " data, we succeeded at applying distribution with a probability of "
              + distValue*100
              + "% while the other probability that allowed us was " + (comparison*100) + "%");
      this.resetCounter();
    }

    LocalDateTime currentDate =
        this.startingDate.plusSeconds(this.timeIncrement * this.counterDate);
    this.counterDate++;
    return currentDate;
  }

  @Override
  public void resetCounter() {
    this.counterDate = 0;
  }
}
