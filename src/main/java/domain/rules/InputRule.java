package domain.rules;

public class InputRule {
  private String id;
  private String condition;
  private int comparator;
  private ConditionModifier result;

  public InputRule(String id, String condition, int comparator, ConditionModifier result) {
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

  public int getComparator() {
    return this.comparator;
  }

  public ConditionModifier getResult() {
    return this.result;
  }
}
