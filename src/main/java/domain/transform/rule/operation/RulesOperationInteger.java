package domain.transform.rule.operation;

import domain.value.Value;

public class RulesOperationInteger implements RulesOperationT<Integer> {
  private String operation;
  private Integer value;

  public RulesOperationInteger(String operation, Double value) {
    this.operation = operation;
    this.value = value.intValue();
  }

  @Override
  public void applyChanges(Value<Integer> output) {
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
