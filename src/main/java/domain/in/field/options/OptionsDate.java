package domain.in.field.options;

/**
 * Class that contains the extra info only available to fields with a date value.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OptionsDate extends Options {
  /** Default value for starting date used in case Options parent is called : {@value} */
  private static final String DEFAULT_STARTING_DATE = "01/01/1970";
  /** Default value for time increment in case Options parent is called: {@value} */
  private static final int DEFAULT_TIME_INCREMENT = 1;

  /** Starting date for the data field. Dates will never go below this value. */
  private String startingDate;
  /** Integer with the distance between each date in seconds. */
  private int timeIncrement;

  /** Constructor, used to set default values in case Options parent was used. */
  public OptionsDate() {
    this.startingDate = DEFAULT_STARTING_DATE;
    this.timeIncrement = DEFAULT_TIME_INCREMENT;
  }

  /**
   * Constructor.
   *
   * @param startingDate String with the starting date.
   * @param timeIncrement Integer with the distance between dates in seconds.
   */
  public OptionsDate(String startingDate, int timeIncrement) {
    this.startingDate = startingDate;
    this.timeIncrement = timeIncrement;
  }

  /**
   * Getter for the starting date.
   *
   * @return A string with the starting date.
   */
  public String getStartingDate() {
    return this.startingDate;
  }

  /**
   * Getter for the time increment.
   *
   * @return An Integer with the time increment.
   */
  public int getTimeIncrement() {
    return this.timeIncrement;
  }
}
