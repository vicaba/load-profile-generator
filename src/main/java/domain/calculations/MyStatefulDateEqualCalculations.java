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
public class MyStatefulDateEqualCalculations implements EqualCalculations<LocalDateTime> {

  /** Starting date. Date values will never go below this one. */
  private LocalDateTime currentDate;

  /** How much time in seconds there is between created dates */
  private int timeIncrement;


  private DateTimeFormatter dateTimeFormatter;

  /**
   * Constructor.
   *
   * @param startingDate String with the initial date. Dates below the initial date will never be
   *     generated.
   * @param timeIncrement Integer with the amount of time that will pass every time a new date is
   *     generated.
   */
  // TODO: Change currentDate type frpm String to a Date specific type. Remove maxDates.
  public MyStatefulDateEqualCalculations(LocalDateTime startingDate, int timeIncrement) {
    this.currentDate = startingDate;
    this.timeIncrement = timeIncrement;
  }

  /**
   * Function responsible of giving a valid date according to the configuration passed in the
   * constructor. The next date will be based according to the formula currentDate +
   * timeIncrement*random(1,maxDates+1).
   *
   * @return A random date that follows the formula mentioned above.
   */
  @Override
  public LocalDateTime calculate() {
    currentDate = currentDate.plusSeconds(this.timeIncrement);
    return currentDate;
  }
}

