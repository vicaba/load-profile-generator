package domain.in.field;

public class InputFieldInfo {

  private String id;
  private String name;
  private String type;

  // TODO: If making it private doesn't break deserialization, make it private
  public InputFieldInfo() {}

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }
}
