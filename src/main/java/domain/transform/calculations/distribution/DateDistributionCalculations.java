package domain.transform.calculations.distribution;

import org.apache.commons.math3.distribution.TDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class DateDistributionCalculations implements DistributionCalculations<LocalDateTime> {
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  private LocalDateTime startingDate;
  private int timeIncrement;
  /*
   * The initial value, that will go from -5 to -Inf.
   * If the initial value is -5, at half the counterData you have a 50% chance to apply the distribution.
   * The smaller the number is, the more data you need to pass so the percentage becomes higher.
   */
  private double offset;
  /*
   * The amount of data you need to pass so the chance to apply the distribution becomes close to 100%.
   * This works on a 10/counterData formula, so the smaller it is, the less it takes to reach 100%.
   * Keep in mind that counterData works on a [-5, 5] range, not on a [initialValue, 5] range.
   */
  private double totalData;
  private int counterDate = 0;
  private int counterDistribution = 0;

  private TDistribution tDistribution = new TDistribution(10); // We use the Student-T probability
  private Logger logger = LoggerFactory.getLogger("CalculationsLogger");

  public DateDistributionCalculations(
      String startingDate, int timeIncrement, double offset, double totalData) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
    this.offset = offset;
    this.totalData = totalData;
    System.out.println("Offset is "+offset+" and totalData is "+totalData);
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
    double comparison = ThreadLocalRandom.current().nextInt(100) * 0.01;
    if (comparison <= distValue) {
      // TODO THIS IS THE IMPORTANT PART IN THE LOGGER
      logger.debug(counterDistribution + ",1");
      System.out.println(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and distValue is "
              + distValue);
      this.counterDistribution = 0;
    } else {
      // TODO THIS IS THE IMPORTANT PART IN THE LOGGER
      logger.debug(counterDistribution + ",0");
      System.out.println(
          "Division is "
              + (10 / totalData)
              + ", operation is "
              + ((counterDistribution * (10 / totalData)) + offset)
              + ", and distValue is "
              + distValue);
      this.counterDistribution++;
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
