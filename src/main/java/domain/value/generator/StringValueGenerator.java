package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;

public class StringValueGenerator extends ValueGenerator<String, Calculations<String>> {

  public StringValueGenerator(InputField inputField, Calculations<String> calculations) {
    super(inputField, calculations);
  }
}
