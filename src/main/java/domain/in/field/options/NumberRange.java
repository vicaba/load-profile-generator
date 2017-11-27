package domain.in.field.options;

/**
 * Class that contains the info that specifies a range of values.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class NumberRange {
  /** Minimum value of the range. By default is the minimum value Java allows. */
  private float fMin = Float.MIN_VALUE;
  /** Maximum value of the range. By default is the maximum value Java allows. */
  private float fMax = Float.MAX_VALUE;

  /**
   * Constructor.
   *
   * @param fMin Float with the minimum value of the range.
   * @param fMax Float with the maximum value of the range.
   */
  public NumberRange(float fMin, float fMax) {
    this.fMin = fMin;
    this.fMax = fMax;
  }

  /**
   * Getter of the minimum value of the range.
   *
   * @return A float with the minimum value of the range.
   */
  public float getMin() {
    return this.fMin;
  }

  /**
   * Getter of the maximum value of the range.
   *
   * @return A float with the maximum value of the range.
   */
  public float getMax() {
    return this.fMax;
  }
}
