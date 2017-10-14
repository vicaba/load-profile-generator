package domain.in.rules;

public class ConditionModifier<T> {

  private String id;
  private String operation;
  private T value;

  public ConditionModifier(String id, String operation, T value) {
    this.id = id;
    this.operation = operation;
    this.value = value;
  }

  public String getId() {
    return this.id;
  }

  public String getOperation() {
    return this.operation;
  }

  public T getValue() {
    return this.value;
  }

}
