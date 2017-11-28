package domain.value.generator;

import domain.in.field.InputField;
import domain.transform.calculations.Calculations;
import domain.transform.calculations.distribution.DistributionCalculations;
import domain.value.Value;

/**
 * Abstract class used to define a value generator that is responsible to create a value class based
 * on an input field, and attaches a calculation class to generate the next data when needed.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> A value type compatible with the Calculations class (i.e. Number).
 * @param <V> A class that extends the Calculations class.
 */
public abstract class ValueGenerator<T, V extends Calculations<T>> {
  /** The InputField that contains the info of the value that will be generated. */
  private InputField inputField;
  /** The Calculations class responsible of generating the next value. */
  private V calculations;

  /**
   * Constructor.
   *
   * @param inputField InputField that contains the info of the value that will be generated.
   * @param calculations Calculations class responsible of generating the next value.
   */
  public ValueGenerator(InputField inputField, V calculations) {
    this.inputField = inputField;
    this.calculations = calculations;
  }

  /**
   * Function responsible to generate the next Value object.
   *
   * @return A value generated using both InputField and the value obtained from the Calculations
   *     class.
   */
  public Value<T> obtainNext() {
    return new Value<>(
        this.inputField.getId(),
        this.inputField.getType(),
        this.inputField.getName(),
        this.calculations.calculate());
  }

  /**
   * Getter of the id inside the InputField.
   *
   * @return A string with the id inside the InputField.
   */
  public String getId() {
    return this.inputField.getId();
  }

  /**
   * Getter of the name inside the InputField.
   *
   * @return A string with the name inside the InputField.
   */
  public String getName() {
    return this.inputField.getName();
  }

  /** Method responsible of increasing the counter inside the DistributionCalculations object. */
  public void increaseCounter() {
    if (this.calculations instanceof DistributionCalculations) {
      ((DistributionCalculations) this.calculations).increaseDistributionCounter();
    }
  }
}
