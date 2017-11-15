package domain.in.field;

public class InputField<Opt> {
  private InputFieldInfo fields;
  private Opt options;
  private DistributionInfo distributionInfo;

  public InputField(InputFieldInfo inputFieldInfo, Opt options, DistributionInfo distributionInfo) {
    this.fields = inputFieldInfo;
    this.options = options;
    this.distributionInfo = distributionInfo;
  }

  public String getId() {
    return this.fields.getId();
  }

  public String getName() {
    return this.fields.getName();
  }

  public String getType() {
    return this.fields.getType();
  }

  public Opt getOptions() {
    return this.options;
  }

  public DistributionInfo getDistributionInfo() {
     return this.distributionInfo;
  }
}
