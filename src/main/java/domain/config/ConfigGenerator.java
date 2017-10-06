package domain.config;

import domain.field.InputField;
import domain.rules.InputRule;

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
  /** ArrayList with the list of rules obtained from the config file. */
  private ArrayList<InputRule> rules;

  /** Constructor. */
  public ConfigGenerator() {
    this.fields = new ArrayList<>();
    this.rules = new ArrayList<>();
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
   * Getter for the list of results value.
   *
   * @return ArrayList with the list of results.
   */
  public ArrayList<InputRule> getRules() {
    return this.rules;
  }

  /**
   * Getter of one specific field inside the list of fields.
   *
   * @param field Integer that indicates which field we get, goes from 0 to length of fields.
   * @return Field that was chosen with the field parameter.
   */
  public InputField getField(int field) {
    return this.fields.get(field);
  }

  /**
   * Getter of one specific field inside the list of rules.
   *
   * @param rule Integer that indicates which field we get, goes from 0 to length of rules.
   * @return Field that was chosen with the rule parameter.
   */
  public InputRule getRule(int rule) {
    return this.rules.get(rule);
  }
}
