package domain.transform.calculations.equal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class responsible of giving a valid date field depending on the input configurations that are
 * passed, using a counter that increases to get the next date.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class DateEqualCalculations implements EqualCalculations<LocalDateTime> {
  /** Defines the format in which the date will appear in the output data. */
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  /** Current date, which will constantly be updated every time we calculate next value. */
  private LocalDateTime currentDate;
  /** How much time in seconds there is between created dates */
  private int timeIncrement;

  /**
   * Constructor.
   *
   * @param startingDate String with the initial date. Dates below the initial date will never be
   *     generated.
   * @param timeIncrement Integer with the amount of time that will pass every time a new date is
   *     generated.
   */
  public DateEqualCalculations(String startingDate, int timeIncrement) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.currentDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
  }

  /**
   * Function responsible of giving a valid date according to the configuration passed in the
   * constructor. The next date will be based according to the formula
   * currentDate=currentDate+timeIncrement.
   *
   * @return A random date that follows the formula mentioned above.
   */
  @Override
  public LocalDateTime calculate() {
    this.currentDate = this.currentDate.plusSeconds(this.timeIncrement);
    return this.currentDate;
  }
}
