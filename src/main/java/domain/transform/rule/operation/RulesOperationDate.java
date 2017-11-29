package domain.transform.rule.operation;

import domain.value.Value;

import java.time.LocalDateTime;

public class RulesOperationDate implements RulesOperationT<LocalDateTime> {
  /** The operation we want to apply. */
  private String operation;
  /** The value used with the operator. */
  private Long value;

  /**
   * Constructor.
   *
   * @param operation The operation we want to apply.
   * @param value The value that we will use with the operator.
   */
  public RulesOperationDate(String operation, Double value) {
    this.operation = operation;
    this.value = value.longValue();
  }

  // TODO Need to make sure that the default behavior if it doesn't match any works well.

  /**
   * The method used to apply changes to the value.
   *
   * @param output The value that we need to make the operation.
   */
  @Override
  public void applyChanges(Value<LocalDateTime> output) {
    switch (operation) {
      case "+":
        output.setValue(output.getValue().plusSeconds(this.value));
        break;
      case "-":
        output.setValue(output.getValue().minusSeconds(this.value));
        break;
      default:
        break;
    }
  }
}
