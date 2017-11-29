package domain.transform.rule;

/**
 * Class that we can use to pass any condition and comparator from InputField to check if it's
 * fulfilled or not.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> The type of the left and right comparator. This assures us that both will have the
 *     same type.
 */
public class RulesCondition<T extends Comparable<? super T>> {
  /** Constants that defines the many different conditions that we can use */
  private static final String IS_LESS_THAN = "isLessThan";

  private static final String IS_GREATER_THAN = "isGreaterThan";
  private static final String IS_EQUAL_THAN = "isEqualThan";
  private static final String IS_LESS_EQUAL_THAN = "isLessEqualThan";
  private static final String IS_GREATER_EQUAL_THAN = "isGreaterEqualThan";
  private static final String IS_NOT_EQUAL_THAN = "isNotEqualThan";

  /** Left comparator of the condition, will have the same type as right comparator. */
  private T leftCondition;
  /** The condition we will check. */
  private String condition;
  /** Right comparator of the condition, will have the same type as left comparator. */
  private T rightCondition;

  /**
   * Constructor.
   *
   * @param leftCondition The left comparator used in the condition.
   * @param condition The condition we will check.
   * @param rightCondition The right comparator used in the condition.
   */
  public RulesCondition(T leftCondition, String condition, T rightCondition) {
    this.leftCondition = leftCondition;
    this.condition = condition;
    this.rightCondition = rightCondition;
  }

  // TODO I need to make sure the default behavior makes sense.

  /**
   * Function that uses the condition and comparators given to check if the condition is fulfilled
   * or not.
   *
   * @return Returns true if the condition is fulfilled, and false if it doesn't.
   */
  public boolean checkResults() {
    switch (this.condition) {
      case IS_LESS_THAN:
        return this.leftCondition.compareTo(this.rightCondition) < 0;
      case IS_EQUAL_THAN:
        return this.leftCondition.compareTo(this.rightCondition) == 0;
      case IS_GREATER_THAN:
        return this.leftCondition.compareTo(this.rightCondition) > 0;
      case IS_LESS_EQUAL_THAN:
        return this.leftCondition.compareTo(this.rightCondition) <= 0;
      case IS_GREATER_EQUAL_THAN:
        return this.leftCondition.compareTo(this.rightCondition) >= 0;
      case IS_NOT_EQUAL_THAN:
        return this.leftCondition.compareTo(this.rightCondition) != 0;
      default:
        return false;
    }
  }
}
