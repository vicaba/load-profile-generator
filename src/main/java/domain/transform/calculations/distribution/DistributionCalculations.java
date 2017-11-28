package domain.transform.calculations.distribution;

import domain.transform.calculations.Calculations;

/**
 * Interface used for calculating with distributions, the distribution used depending on the one
 * chosen in the inputField.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> Type of element that will be returned after calculations.
 */
public interface DistributionCalculations<T> extends Calculations<T> {
  /**
   * Method that increases the distribution counter, which needs to be called from the
   * DistributionFlow.
   */
  void increaseDistributionCounter();
}
