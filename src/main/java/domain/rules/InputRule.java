package domain.rules;

public class InputRule<T> {
  private String id;
  private String condition;
  private T comparator;
  private ConditionModifier result;

  public InputRule(String id, String condition, T comparator, ConditionModifier result) {
    this.id = id;
    this.condition = condition;
    this.comparator = comparator;
    this.result = result;
  }

  public String getId() {
    return this.id;
  }

  public String getCondition() {
    return this.condition;
  }

  public T getComparator() {
    return this.comparator;
  }

  public ConditionModifier getResult() {
    return this.result;
  }
}
