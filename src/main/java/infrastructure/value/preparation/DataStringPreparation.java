package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.in.field.options.OptionsString;
import domain.transform.calculations.StringEqualCalculations;
import domain.value.Value;

public class DataStringPreparation {
  private InputField<OptionsString> inputField;

  public DataStringPreparation(InputField<OptionsString> inputField) {
    this.inputField = inputField;
  }

  public Value<String> obtainPreparedValue() {
    OptionsString optionsString = inputField.getOptions();
    StringEqualCalculations stringEqualCalculations =
        new StringEqualCalculations(optionsString.getAcceptedStrings());
    return new Value<>(
        inputField.getId(), inputField.getType(), stringEqualCalculations.calculate());
  }
}
