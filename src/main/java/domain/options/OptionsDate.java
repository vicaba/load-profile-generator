package domain.options;

public class OptionsDate extends Options {
  private String startingDate;
  private int timeIncrement;

  public OptionsDate() {}

  public String getStartingDate() {
    return this.startingDate;
  }

  public int getTimeIncrement() {
    return this.timeIncrement;
  }
}
