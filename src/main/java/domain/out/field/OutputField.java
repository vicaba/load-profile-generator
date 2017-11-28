package domain.out.field;

/**
 * Class that contains all the info to output the data.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OutputField {
  /** What type of file we want to work (i.e. json). */
  private String type;

  /**
   * Constructor.
   *
   * @param type Type of file we want to output.
   */
  public OutputField(String type) {
    this.type = type;
  }

  /**
   * Getter of the type.
   *
   * @return A string with the type of file we want to output.
   */
  public String getType() {
    return this.type;
  }
}
