package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.transform.calculations.DateEqualCalculations;
import domain.value.Value;

import java.time.LocalDateTime;

public class DataDatePreparation {
  private InputField<OptionsDate> inputField;
  private int cycle;

  public DataDatePreparation(InputField<OptionsDate> inputField, int cycle) {
    this.inputField = inputField;
    this.cycle = cycle;
  }

  public Value<LocalDateTime> obtainPreparedValue() {
    OptionsDate optionsDate = this.inputField.getOptions();
    DateEqualCalculations dateEqualCalculations =
        new DateEqualCalculations(
            optionsDate.getStartingDate(), optionsDate.getTimeIncrement(), cycle);

    return new Value<>(
        this.inputField.getId(), this.inputField.getType(), dateEqualCalculations.calculate());
  }

}
