package output.rule;

public class RulesCondition<T extends Comparable<T>> {
  private T leftCondition;
  private String condition;
  private T rightCondition;

  public RulesCondition(T leftCondition, String condition, T rightCondition) {
    this.leftCondition = leftCondition;
    this.condition = condition;
    this.rightCondition = rightCondition;
  }

  public boolean checkResults() {
    switch (this.condition) {
      case "isLess":
        return this.leftCondition.compareTo(this.rightCondition) < 0;
      case "isEqual":
        return this.leftCondition.compareTo(this.rightCondition) == 0;
      default:
        return false;
    }
  }
}
