package domain.in.rule;

import domain.in.condition.ConditionModifier;
import util.functional.Functor;

import java.util.function.Function;

/**
 * Class that contains all the info of a rule.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> Generic type that specifies the value of the right comparator
 */
public class InputRule<T> implements Functor<T, InputRule<?>> {
  /**
   * Id of the field in which we will check if it fulfills the rule condition, used as left
   * comparator.
   */
  private String id;
  /** The condition of the rule. */
  private String condition;
  /** The right comparator, its value is specified in execution time. */
  private T comparator;
  /** Object that specifies what action will be taken if the condition is fulfilled. */
  private ConditionModifier result;

  /**
   * Constructor.
   *
   * @param id String with the id of the field.
   * @param condition String with the condition that will be applied.
   * @param comparator Right comparator, its value specified in execution time.
   * @param result ConditionModifier with the action that will be taken.
   */
  public InputRule(String id, String condition, T comparator, ConditionModifier result) {
    this.id = id;
    this.condition = condition;
    this.comparator = comparator;
    this.result = result;
  }

  /**
   * Getter of the id.
   *
   * @return A String with the id of the field.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Getter of the condition.
   *
   * @return A String with the condition.
   */
  public String getCondition() {
    return this.condition;
  }

  /**
   * Getter of the right comparator.
   *
   * @return The right comparator.
   */
  public T getComparator() {
    return this.comparator;
  }

  /**
   * Getter of the result.
   *
   * @return A ConditionModifier with the action that will be taken.
   */
  public ConditionModifier getResult() {
    return this.result;
  }

  /**
   * Function that allows us to implement any value for the right comparator in the constructor by
   * mapping it to InputRule using the Function class.
   *
   * @param f The Function class that will help us map any value to InputRule's right comparator.
   * @param <R> The generic type in which we specify the type of value the InputRule's right
   *     comparator will have.
   * @return An InputRule object that will have its right comparator with the desired value.
   */
  @Override
  public <R> InputRule<R> map(Function<T, R> f) {
    return new InputRule<>(id, condition, f.apply(comparator), result);
  }
}
