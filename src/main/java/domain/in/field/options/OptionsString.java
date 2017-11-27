package domain.in.field.options;

import java.util.ArrayList;

/**
 * Class that contains the extra info only available to fields with a String value.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OptionsString extends Options {
  /** ArrayList with all the Strings that will appear on this field. */
  private ArrayList<String> acceptedStrings;

  /** Constructor. */
  public OptionsString() {
    acceptedStrings = new ArrayList<>();
  }

  /**
   * Function that returns how many Strings are available for this field.
   *
   * @return The size of the ArrayList of accepted strings.
   */
  public int getSizeAccepted() {
    return this.acceptedStrings.size();
  }

  /**
   * Getter for the accepted strings.
   *
   * @return An ArrayList with all the accepted strings.
   */
  public ArrayList<String> getAcceptedStrings() {
    return this.acceptedStrings;
  }

  /**
   * Getter for one specific string from the list of accepted Strings.
   *
   * @param iWhich Integer with the position of the ArrayList, must be between [0, array.size-1]
   * @return Returns the string specified in the passed parameter.
   */
  public String getAcceptedString(int iWhich) {
    return this.acceptedStrings.get(iWhich);
  }
}
