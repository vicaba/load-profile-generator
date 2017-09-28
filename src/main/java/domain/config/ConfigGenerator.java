package domain.config;

import domain.field.InputField;

import java.util.ArrayList;

/**
 * Class used to obtain and keep all the information regarding the input configurations.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 28/09/2017
 */
public class ConfigGenerator {
  /** ArrayList with the list of fields obtained from the config file. */
  private ArrayList<InputField> fields;
  /** The amount of fields inside the list of fields */
  private int length;

  /** Constructor. */
  public ConfigGenerator() {
    fields = new ArrayList<>();
  }

  /**
   * Getter for the list of fields value.
   *
   * @return ArrayList with the list of fields.
   */
  public ArrayList<InputField> getFields() {
    return this.fields;
  }

  /**
   * Getter for the length value.
   *
   * @return Integer with the length of the list of fields.
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Getter of one specific field inside the list of fieds.
   *
   * @param field Integer that indicates which field we get, goes from 0 to length.
   * @return Field that was chosen with the field parameter.
   */
  public InputField getField(int field) {
    return fields.get(field);
  }
}
