package domain.transform.calculations.distribution;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateDistributionCalculations implements DistributionCalculations<LocalDateTime> {
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

  private LocalDateTime startingDate;
  private int timeIncrement;
  private int counterDate = 0;

  public DateDistributionCalculations(String startingDate, int timeIncrement) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    this.startingDate = LocalDateTime.parse(startingDate, dateTimeFormatter);
    this.timeIncrement = timeIncrement;
  }

  @Override
  public LocalDateTime calculate() {
    LocalDateTime currentDate =
        this.startingDate.plusSeconds(this.timeIncrement * this.counterDate);
    this.counterDate++;
    return currentDate;
  }

  @Override
  public void resetCounter() {
    this.counterDate = 0;
  }
}
