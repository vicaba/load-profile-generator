package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.transform.calculations.DateEqualCalculations;

import java.time.LocalDateTime;

public class DateValueGenerator extends ValueGenerator<LocalDateTime, DateEqualCalculations> {

  public DateValueGenerator(
      InputField<OptionsDate> inputField, DateEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
