package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;

import java.time.LocalDateTime;

public class DateValueGenerator extends ValueGenerator<LocalDateTime, Calculations<LocalDateTime>> {

  public DateValueGenerator(InputField inputField, Calculations<LocalDateTime> calculations) {
    super(inputField, calculations);
  }
}
