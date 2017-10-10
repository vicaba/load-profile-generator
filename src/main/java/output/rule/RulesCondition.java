package output.rule;

public class RulesCondition<T extends Comparable<? super T>> {
  private T leftCondition;
  private String condition;
  private T rightCondition;

  private static final String IS_LESS_THAN = "isLessThan";
  private static final String IS_GREATER_THAN = "isGreaterThan";
  private static final String IS_EQUAL_THAN = "isEqualThan";
  private static final String IS_LESS_EQUAL_THAN = "isLessEqualThan";
  private static final String IS_GREATER_EQUAL_THAN = "isGreaterEqualThan";
  private static final String IS_NOT_EQUAL_THAN = "isNotEqualThan";



  public RulesCondition(T leftCondition, String condition, T rightCondition) {
    this.leftCondition = leftCondition;
    this.condition = condition;
    this.rightCondition = rightCondition;
  }

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
