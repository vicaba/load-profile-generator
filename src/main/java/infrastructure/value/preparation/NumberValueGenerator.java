package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.equal.NumberEqualCalculations;

public class NumberValueGenerator extends ValueGenerator<Float, NumberEqualCalculations> {
  public NumberValueGenerator(InputField inputField, NumberEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
