package domain.output;

public class Output<T> {
  private String type;
  private T value;

  public Output(String type, T value) {
    this.type = type;
    this.value = value;
  }
}
