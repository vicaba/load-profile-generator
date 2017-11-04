package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;

public class NumberValueGenerator extends ValueGenerator<Float, Calculations<Float>> {
  public NumberValueGenerator(InputField inputField, Calculations<Float> calculations) {
    super(inputField, calculations);
  }
}
