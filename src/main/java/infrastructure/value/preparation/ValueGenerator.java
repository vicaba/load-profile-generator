package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;
import domain.transform.calculations.distribution.DateDistributionCalculations;
import domain.transform.calculations.distribution.DistributionCalculations;
import domain.value.Value;

public abstract class ValueGenerator<T, V extends Calculations<T>> {

  private InputField inputField;

  private V calculations;

  public ValueGenerator(InputField inputField, V calculations) {
    this.inputField = inputField;
    this.calculations = calculations;
  }

  public Value<T> obtainNext() {
    return new Value<>(
        this.inputField.getId(), this.inputField.getType(), this.calculations.calculate());
  }

  public String getId() {
    return this.inputField.getId();
  }

  public String getName() {
    return this.inputField.getName();
  }

  /*
  public void resetGenerator() {
    if (calculations instanceof DistributionCalculations) {
      ((DistributionCalculations) calculations).resetCounter();
    }
  }
  */
}
