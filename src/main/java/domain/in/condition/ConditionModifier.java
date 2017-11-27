package domain.in.condition;

/**
 * Class that contains the operation that will be applied if a rule is triggered.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> Specifies the type of value used for the operation specified inside.
 */
public class ConditionModifier<T> {
  /** Id of the node that you will apply the operation. */
  private String id;
  /** The name of the operation that will be applied. */
  private String operation;
  /** Value used for the operation that will be applied. i.e. the right operand for a mathematical operation. */
  private T value;

  /**
   * Constructor.
   *
   * @param id String with the id of the data that will be modified.
   * @param operation String with the name of the operation that will be applied.
   * @param value Value used for the operation that will be applied.
   */
  public ConditionModifier(String id, String operation, T value) {
    this.id = id;
    this.operation = operation;
    this.value = value;
  }

  /**
   * Getter of the Id.
   *
   * @return A string with the id.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Getter of the operation.
   *
   * @return A string with the operation.
   */
  public String getOperation() {
    return this.operation;
  }

  /**
   * Getter of the value.
   *
   * @return The value used with the operation.
   */
  public T getValue() {
    return this.value;
  }
}
