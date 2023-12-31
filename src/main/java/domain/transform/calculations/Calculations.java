package domain.transform.calculations;

/**
 * Interface responsible of the calculations used to decided which data will be generated.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 * @param <T> Type of element that will be returned after calculations.
 */
public interface Calculations<T> {
  /**
   * Function that returns the result of the calculations made.
   *
   * @return Element obtained after calculations.
   */
  T calculate();
}
