package domain.transform.calculations.equal;

import domain.in.field.options.NumberRange;

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
public class NumberEqualCalculations implements EqualCalculations<Float> {
  /** List of ranges, one of them will be chosen at random. */
  private ArrayList<NumberRange> numberRanges;

  /**
   * Constructor.
   *
   * @param numberRanges ArrayList of NumberRange with the list of ranges available for this field.
   */
  public NumberEqualCalculations(ArrayList<NumberRange> numberRanges) {
    this.numberRanges = numberRanges;
  }

  /**
   * Function responsible of giving a valid number according to the configuration passed in the
   * constructor. The number will be chosen by first choosing a random range from the list with a
   * probability of 1/numberRanges.size, and after that a number will be chosen between the given
   * range using the formula randomFloat * (max-min) + min, including min and excluding max value.
   *
   * @return Float with the random number obtaining after choosing a random range and number.
   */
  @Override
  public Float calculate() {
    // First we obtain the random range from the list of ranges
    NumberRange numberRange =
        this.numberRanges.get(ThreadLocalRandom.current().nextInt(0, this.numberRanges.size()));

    // Then we obtain a random number between inclusive min and exclusive max of that range.
    float fMin = numberRange.getMin();
    return ThreadLocalRandom.current().nextFloat() * (numberRange.getMax() - fMin) + fMin;
  }
}
