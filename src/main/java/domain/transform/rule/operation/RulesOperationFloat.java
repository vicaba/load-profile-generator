package domain.transform.rule.operation;

import domain.out.field.Output;

public class RulesOperationFloat implements RulesOperationT<Float> {
  private String operation;
  private Float value;

  public RulesOperationFloat(String operation, Double value) {
    this.operation = operation;
    this.value = value.floatValue();
  }

  @Override
  @SuppressWarnings("Duplicates")
  public void applyChanges(Output<Float> output) {
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
