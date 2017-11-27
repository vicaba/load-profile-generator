package domain.in.field;

/**
 * Class that contains all the info of an InputField.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 * @param <Opt> Specifies the type of Option we're working with this InputField.
 */
public class InputField<Opt> {
  /** Object with the basic info of the InputField. */
  private InputFieldInfo fields;
  /** Object with specific info that depends of the type of value this field contains. */
  private Opt options;
  /**
   * Object with the distribution info that will be used for this node if it's used to distribute
   * another.
   */
  private DistributionInfo distributionInfo;

  /**
   * Constructor.
   *
   * @param inputFieldInfo Object with the basic info of the InputField.
   * @param options Object with specific info that depends of the type of value this field contains.
   * @param distributionInfo Object with the distribution info that will be used for this node if
   *     it's used to distribute another.
   */
  public InputField(InputFieldInfo inputFieldInfo, Opt options, DistributionInfo distributionInfo) {
    this.fields = inputFieldInfo;
    this.options = options;
    this.distributionInfo = distributionInfo;
  }

  /**
   * Getter of the id.
   *
   * @return String with the id of the field.
   */
  public String getId() {
    return this.fields.getId();
  }

  /**
   * Getter of the name.
   *
   * @return String with the name of the field.
   */
  public String getName() {
    return this.fields.getName();
  }

  /**
   * Getter of the type of value.
   *
   * @return String with the type of value the field has.
   */
  public String getType() {
    return this.fields.getType();
  }

  /**
   * Getter of the Options object.
   *
   * @return The Options that the field has.
   */
  public Opt getOptions() {
    return this.options;
  }

  /**
   * Getter of the DistributionInfo object.
   *
   * @return The DistributionInfo that the field has.
   */
  public DistributionInfo getDistributionInfo() {
    return this.distributionInfo;
  }
}
