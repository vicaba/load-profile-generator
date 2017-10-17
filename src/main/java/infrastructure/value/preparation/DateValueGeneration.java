package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.transform.calculations.DateEqualCalculations;

import java.time.LocalDateTime;

public class DateValueGeneration extends ValueGeneration<LocalDateTime, DateEqualCalculations> {

  public DateValueGeneration(
      InputField<OptionsDate> inputField, DateEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
