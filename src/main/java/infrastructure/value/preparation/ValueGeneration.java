package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;
import domain.value.Value;

public abstract class ValueGeneration<T, V extends Calculations<T>> {
  private InputField inputField;
  private V calculations;

  public ValueGeneration(InputField inputField, V calculations) {
    this.inputField = inputField;
    this.calculations = calculations;
  }

  public Value<T> obtainNext() {
    return new Value<>(
        this.inputField.getId(), this.inputField.getType(), this.calculations.calculate());
  }
}
