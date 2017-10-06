package domain.field;

public class ConditionResult {
  private String id;
  private String operation;
  private int value;

  public ConditionResult(String id, String operation, int value) {
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
