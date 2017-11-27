package domain.in.field.options;

import java.util.ArrayList;

/**
 * Class that contains the extra info only available to fields with a number value.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OptionsNumber extends Options {
  /** ArrayList with all the range of values that the field can have. */
  private ArrayList<NumberRange> ranges;

  /** Constructor. */
  public OptionsNumber() {
    this.ranges = new ArrayList<>();
  }

  /**
   * Getter for the list of ranges.
   *
   * @return An ArrayList with all the ranges.
   */
  public ArrayList<NumberRange> getRanges() {
    return this.ranges;
  }
}
