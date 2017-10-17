package domain.transform.rule.operation;

import domain.value.Value;

public class RulesOperationNumber implements RulesOperationT<Float> {
  private String operation;
  private Float value;

  public RulesOperationNumber(String operation, Double value) {
    this.operation = operation;
    this.value = value.floatValue();
  }

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
