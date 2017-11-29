package domain.transform.rule.operation;

import domain.value.Value;

/**
 * Interface used to create classes that apply the operations to the values. The contents will
 * depend depending on the type of the value.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> The type of the value we need to do an operation.
 */
public interface RulesOperationT<T> {
  /**
   * The method we have to define in each class that implements this class to apply changes to the
   * value.
   *
   * @param output The value that we need to make the operation.
   */
  void applyChanges(Value<T> output);
}
