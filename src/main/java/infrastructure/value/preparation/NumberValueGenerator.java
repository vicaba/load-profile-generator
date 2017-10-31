package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;
import domain.transform.calculations.equal.NumberEqualCalculations;

public class NumberValueGenerator extends ValueGenerator<Float, Calculations<Float>> {
  public NumberValueGenerator(InputField inputField, Calculations<Float> calculations) {
    super(inputField, calculations);
  }
}
