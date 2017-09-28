package domain.options;

public class OptionsDate extends Options {
  private String startingDate;
  private int maxDates;
  private int timeIncrement;

  public OptionsDate() {}

  public String getStartingDate() {
    return this.startingDate;
  }

  public int getMaxDates() {
    return this.maxDates;
  }

  public int getTimeIncrement() {
    return this.timeIncrement;
  }
}
