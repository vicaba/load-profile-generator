package domain.in.field.options;

import java.util.ArrayList;

public class OptionsNumber extends Options {

  private ArrayList<NumberRange> ranges;

  public OptionsNumber() {
    this.ranges = new ArrayList<>();
  }

  public ArrayList<NumberRange> getRanges() {
    return this.ranges;
  }

}
