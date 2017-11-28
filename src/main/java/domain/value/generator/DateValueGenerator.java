package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;

import java.time.LocalDateTime;

/**
 * Class used to generate Date Values with the Value Generator.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class DateValueGenerator extends ValueGenerator<LocalDateTime, Calculations<LocalDateTime>> {

  /**
   * Constructor.
   *
   * @param inputField InputField that contains the info of the value that will be generated.
   * @param calculations Calculations class responsible of generating the next value.
   */
  public DateValueGenerator(InputField inputField, Calculations<LocalDateTime> calculations) {
    super(inputField, calculations);
  }
}
