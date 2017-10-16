package domain.transform.rule.operation;

import domain.value.Value;

public class RulesOperationString implements RulesOperationT<String> {
  private String operation;
  private String value;

  public RulesOperationString(String operation, String value) {
    this.operation = operation;
    this.value = value;
  }

  @Override
  public void applyChanges(Value<String> output) {
    switch (this.operation) {
      case "+S":
        output.setValue(output.getValue() + this.value);
        break;
      case "S+":
        output.setValue(this.value + output.getValue());
        break;
      case "=":
        output.setValue(this.value);
        break;
      default:
        break;
    }
  }
}
