package domain.field;

public class InputRule {
  private String id;
  private String condition;
  private int comparator;
  private ConditionResult result;

  public InputRule(String id, String condition, int comparator, ConditionResult result) {
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

  public ConditionResult getResult() {
    return this.result;
  }
}
