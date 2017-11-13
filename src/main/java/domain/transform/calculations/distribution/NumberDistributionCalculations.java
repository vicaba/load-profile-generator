package domain.transform.calculations.distribution;

import domain.in.field.options.NumberRange;
import domain.transform.calculations.equal.EqualCalculations;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible of giving a valid number field depending on the input configurations that are
 * passed.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class NumberDistributionCalculations implements DistributionCalculations<Number> {
  /** List of ranges, one of them will be chosen at random. */
  private ArrayList<NumberRange> numberRanges;

  private int counterNumber = 0;

  /**
   * Constructor.
   *
   * @param numberRanges ArrayList of NumberRange with the list of ranges available for this field.
   */
  public NumberDistributionCalculations(ArrayList<NumberRange> numberRanges) {
    this.numberRanges = numberRanges;
  }

  /**
   * Function responsible of giving a valid number according to the configuration passed in the
   * constructor. The number will be chosen by first choosing a random range from the list, and
   * after that a number will be chosen between the given range
   *
   * <p>The formula used to obtain the number between the chosen range is randomFloat * (max-min) +
   * min, including min and excluding max value.
   *
   * @return Float with the random number obtaining after choosing a random range and number.
   */
  @Override
  public Float calculate() {
    /*
    NumberRange numberRange =
        this.numberRanges.get(ThreadLocalRandom.current().nextInt(0, this.numberRanges.size()));
    */
    NumberRange numberRange = this.numberRanges.get(0);    //TODO Make it compatible with multiple ranges.
    
    float fTotal = numberRange.getMin() + this.counterNumber;
    float fMax = numberRange.getMax();
    //return ThreadLocalRandom.current().nextFloat() * (numberRange.getMax() - fMin) + fMin;
    if (fTotal >= fMax) return fMax;
    this.counterNumber++;
    return fTotal;
  }

  @Override
  public void resetCounter() {
    this.counterNumber = 0;
  }
}
