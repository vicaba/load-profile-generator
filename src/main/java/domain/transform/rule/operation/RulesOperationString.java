package domain.transform.rule.operation;

import domain.out.field.Output;

public class RulesOperationString implements RulesOperationT<String> {
  private String operation;
  private String value;

  public RulesOperationString(String operation, String value) {
    this.operation = operation;
    this.value = value;
  }

  @Override
  public void applyChanges(Output<String> output) {
    switch (this.operation) {
      case "+S":
        output.setValue(output.getValue() + this.value);
        break;
      case "S+":
        output.setValue(this.value + output.getValue());
        break;
      case "=":
        output.setValue(output.getValue());
        break;
      default:
        break;
    }
  }
}
