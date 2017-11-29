package domain.transform.rule.operation;

import domain.value.Value;

/**
 * Class that is used to implement operations to String values.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class RulesOperationString implements RulesOperationT<String> {
  /** The operation we want to apply. */
  private String operation;
  /** The value used with the operator. */
  private String value;

  /**
   * Constructor.
   *
   * @param operation The operation we want to apply.
   * @param value The value that we will use with the operator.
   */
  public RulesOperationString(String operation, String value) {
    this.operation = operation;
    this.value = value;
  }

  // TODO Need to make sure that the default behavior if it doesn't match any works well.

  /**
   * The method used to apply changes to the value.
   *
   * @param output The value that we need to make the operation.
   */
  @Override
  public void applyChanges(Value<String> output) {
    switch (this.operation) {
      case "+S":
        // Attaches a String to the right of the String inside the value.
        output.setValue(output.getValue() + this.value);
        break;
      case "S+":
        // Attaches a String to the left of the String inside the value.
        output.setValue(this.value + output.getValue());
        break;
      case "=":
        // Changes the String inside the value with the one added in this class.
        output.setValue(this.value);
        break;
      default:
        break;
    }
  }
}
