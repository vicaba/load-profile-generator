package domain.transform.rule.operation;

import domain.value.Value;

/**
 * Class that is used to implement operations to Float values.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class RulesOperationNumber implements RulesOperationT<Float> {
  /** The operation we want to apply. */
  private String operation;
  /** The value used with the operator. */
  private Float value;

  /**
   * Constructor.
   *
   * @param operation The operation we want to apply.
   * @param value The value that we will use with the operator.
   */
  public RulesOperationNumber(String operation, Double value) {
    this.operation = operation;
    this.value = value.floatValue();
  }

  // TODO Need to make sure that the default behavior if it doesn't match any works well.

  /**
   * The method used to apply changes to the value.
   *
   * @param output The value that we need to make the operation.
   */
  @Override
  public void applyChanges(Value<Float> output) {
    switch (operation) {
      case "+":
        output.setValue(output.getValue() + this.value);
        break;
      case "-":
        output.setValue(output.getValue() - this.value);
        break;
      case "*":
        output.setValue(output.getValue() * this.value);
        break;
      case "/":
        output.setValue(output.getValue() / this.value);
        break;
      case "%":
        output.setValue(output.getValue() % this.value);
        break;
      case "=":
        output.setValue(this.value);
        break;
    }
  }
}
