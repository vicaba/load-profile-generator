package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;

/**
 * Class used to generate Number Values with the Value Generator.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class NumberValueGenerator extends ValueGenerator<Float, Calculations<Float>> {

  /**
   * Constructor.
   *
   * @param inputField InputField that contains the info of the value that will be generated.
   * @param calculations Calculations class responsible of generating the next value.
   */
  public NumberValueGenerator(InputField inputField, Calculations<Float> calculations) {
    super(inputField, calculations);
  }
}
