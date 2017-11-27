package domain.in.field;

/**
 * Class that contains the basic information of a field, that is common to all fields.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class InputFieldInfo {
  /** Id of the field, it's a unique value between all fields. */
  private String id;
  /** The name of the field, used for information purpose only. */
  private String name;
  /** The type of value this field contain. */
  private String type;

  /**
   * Constructor.
   *
   * @param id String with the Id of the field.
   * @param name String with the name of the field
   * @param type String with the type of value this field contain.
   */
  private InputFieldInfo(String id, String name, String type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }

  /**
   * Getter of the Id
   *
   * @return A string with the id of the field.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Getter of the name.
   *
   * @return String with the name of the field.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter of the type.
   *
   * @return String with the type of value used in the field.
   */
  public String getType() {
    return this.type;
  }
}
