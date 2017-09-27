package domain.calculations;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class StringEqualCalculations implements EqualCalculations<String> {
  private ArrayList<String> acceptedStrings;
  private int totalStrings;

  public StringEqualCalculations(ArrayList<String> acceptedStrings) {
    this.acceptedStrings = acceptedStrings;
    this.totalStrings = acceptedStrings.size();
  }

  @Override
  public String calculate() {
    int randomValue = ThreadLocalRandom.current().nextInt(0, this.totalStrings);
    return this.acceptedStrings.get(randomValue);
  }
}
