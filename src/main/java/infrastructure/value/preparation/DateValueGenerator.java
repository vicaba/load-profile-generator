package infrastructure.value.preparation;

import domain.in.field.InputField;
import domain.transform.calculations.equal.DateEqualCalculations;

import java.time.LocalDateTime;

public class DateValueGenerator extends ValueGenerator<LocalDateTime, DateEqualCalculations> {

  public DateValueGenerator(InputField inputField, DateEqualCalculations calculations) {
    super(inputField, calculations);
  }
}
