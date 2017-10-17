package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.StringEqualCalculations;

public class StringValueGenerator extends ValueGenerator<String, StringEqualCalculations> {

  public StringValueGenerator(InputField inputField, StringEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
