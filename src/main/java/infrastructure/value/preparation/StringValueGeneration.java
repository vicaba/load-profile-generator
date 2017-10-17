package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.StringEqualCalculations;

public class StringValueGeneration extends ValueGeneration<String, StringEqualCalculations> {

  public StringValueGeneration(InputField inputField, StringEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
