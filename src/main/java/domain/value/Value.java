package domain.value;

/**
 * Class that contains all the info of a value, with a list of values being a full data value.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <T> Generic type that specifies the value of the data
 */
public class Value<T> {
  /** Id of the source/flow that generated this value. */
  private String id;
  /** Type of the value. */
  private String type;
  /** Name of the value. */
  private String name;
  /** Value saved inside this class. */
  private T value;

  /**
   * Constructor.
   *
   * @param id String with the Id of the source/flow that generated this class.
   * @param type String with the type of the value.
   * @param value String with the value that is saved inside this class.
   */
  public Value(String id, String type, String name, T value) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.value = value;
  }

  /**
   * Getter of the Id.
   *
   * @return A String with the Id of the source/flow that generated this class.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Getter of the type.
   *
   * @return A String with the type of the value (i.e. date).
   */
  public String getType() {
    return this.type;
  }

  /**
   * Getter of the name.
   * @return A String with the name of the value.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter of the value.
   *
   * @return A String with the value that is saved inside this class.
   */
  public T getValue() {
    return this.value;
  }

  /**
   * Setter of the value.
   *
   * @param value New value to add to this class.
   */
  public void setValue(T value) {
    this.value = value;
  }
}
