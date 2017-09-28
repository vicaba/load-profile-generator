package domain.calculations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class responsible of giving a valid date field depending on the input configurations that are
 * passed.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class DateEqualCalculations implements EqualCalculations<String> {
  /** Defines the format in which the date will appear in the output data. */
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  /** Starting date. Date values will never go below this one. */
  private String startingDate;
  /** How much time in seconds there is between created dates */
  private int timeIncrement;
  /** The current cycle. Used to choose the next X date after the starting date */
  private int cycle;

  private DateTimeFormatter dateTimeFormatter;

  /**
   * Constructor.
   *
   * @param startingDate String with the initial date. Dates below the initial date will never be
   *     generated.
   * @param timeIncrement Integer with the amount of time that will pass every time a new date is
   *     generated.
   * @param cycle Integer with the current data cycle we are in at the moment.
   */
  public DateEqualCalculations(String startingDate, int timeIncrement, int cycle) {
    this.dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = startingDate;
    this.timeIncrement = timeIncrement;
    this.cycle = cycle;
  }

  /**
   * Function responsible of giving a valid date according to the configuration passed in the
   * constructor. The next date will be based according to the formula startingDate +
   * timeIncrement*cycle.
   *
   * @return A valid date according to the formula mentioned above.
   */
  @Override
  public String calculate() {
    LocalDateTime localDateTime =
        LocalDateTime.parse(this.startingDate, this.dateTimeFormatter)
            .plusSeconds(this.timeIncrement * this.cycle);

    return localDateTime.format(dateTimeFormatter);
  }
}
