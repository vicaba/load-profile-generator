package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;
import domain.transform.calculations.equal.StringEqualCalculations;

public class StringValueGenerator extends ValueGenerator<String, Calculations<String>> {

  public StringValueGenerator(InputField inputField, Calculations<String> calculations) {
    super(inputField, calculations);
  }
}
