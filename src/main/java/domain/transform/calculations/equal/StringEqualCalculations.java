package domain.transform.calculations.equal;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible of giving a valid string field depending on the input configurations that are
 * passed.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class StringEqualCalculations implements EqualCalculations<String> {
  /** List of Strings, one will be chosen at random from this list. */
  private ArrayList<String> acceptedStrings;

  private int totalStrings;

  /**
   * Constructor.
   *
   * @param acceptedStrings ArrayList of String with the list of values that this field can have.
   */
  public StringEqualCalculations(ArrayList<String> acceptedStrings) {
    this.acceptedStrings = acceptedStrings;
    this.totalStrings = acceptedStrings.size();
  }

  /**
   * Function responsible of giving a valid String according to the configuration passed in the
   * constructor.
   *
   * @return Returns the randomly chosen String from the list we have.
   */
  @Override
  public String calculate() {
    int randomValue = ThreadLocalRandom.current().nextInt(0, this.totalStrings);
    return this.acceptedStrings.get(randomValue);
  }
}
