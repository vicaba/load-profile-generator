package domain.output;

public class Output<T> {
  private String id;
  private String type;
  private T value;

  public Output(String id, String type, T value) {
    this.id = id;
    this.type = type;
    this.value = value;
  }

  public String getId() {
    return this.id;
  }

  public T getValue() {
    return this.value;
  }
}
