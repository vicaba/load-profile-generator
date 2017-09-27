package domain.options;

import java.util.ArrayList;

public class OptionsNumber extends Options {
  private String type;
  private ArrayList<NumberRange> ranges;

  public OptionsNumber() {
    this.ranges = new ArrayList<>();
  }

  public ArrayList<NumberRange> getRanges() {
    return this.ranges;
  }

  public String getType() {
    return this.type;
  }
}
