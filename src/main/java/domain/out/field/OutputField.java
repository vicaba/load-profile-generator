package domain.out.field;

/**
 * Class that contains all the info to output the data.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OutputField {
  /** The name of the domain.template to be used by the domain.template engine. */
  private String nameTemplate;
  /** What type of file we want to work (i.e. json). */
  private String outputType;

  /**
   * Constructor.
   *
   * @param nameTemplate The name of the domain.template to be used.
   * @param outputType Type of file we want to output.
   */
  public OutputField(String nameTemplate, String outputType) {
    this.nameTemplate = nameTemplate;
    this.outputType = outputType;
  }

  /**
   * Getter of the name of the domain.template.
   *
   * @return Returns a string with the name of the domain.template that will be used by the domain.template
   *     engine.
   */
  public String getNameTemplate() {
    return this.nameTemplate;
  }

  /**
   * Getter of the type.
   *
   * @return A string with the type of file we want to output.
   */
  public String getOutputType() {
    return this.outputType;
  }
}
