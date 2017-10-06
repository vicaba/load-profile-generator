package domain.rules;

public class ConditionModifier {
  private String id;
  private String operation;
  private int value;

  public ConditionModifier(String id, String operation, int value) {
    this.id = id;
    this.operation = operation;
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public String getOperation() {
    return operation;
  }

  public int getValue() {
    return value;
  }
}
