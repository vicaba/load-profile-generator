package domain.transform.rule.operation;

import domain.value.Value;

import java.time.LocalDateTime;

public class RulesOperationDate implements RulesOperationT<LocalDateTime> {
  private String operation;
  private Long value;

  public RulesOperationDate(String operation, Double value) {
    this.operation = operation;
    this.value = value.longValue();
  }

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
