package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.NumberEqualCalculations;

public class NumberValueGeneration extends ValueGeneration<Float, NumberEqualCalculations> {
  public NumberValueGeneration(InputField inputField, NumberEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
