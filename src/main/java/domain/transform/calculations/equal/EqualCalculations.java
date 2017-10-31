package domain.transform.calculations.equal;

import domain.transform.calculations.Calculations;

/**
 * Interface used for calculating with the probabilistic method 1/n, n being the total amount of
 * data from which we have to choose.
 *
 * @param <T> Type of element that will be returned after calculations.
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public interface EqualCalculations<T> extends Calculations<T> {}
