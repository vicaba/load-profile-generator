package domain.in.field;

/**
 * Class that contains the stream.distribution information of a field, if the field is used to distributed another.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class DistributionInfo {
  /** Default stream.distribution method in case the json is missing this parameter: {@value} */
  private static final String DEFAULT_DISTRIBUTION_METHOD = "Student-T";
  /** Default offset value in case the json is missing this parameter: {@value} */
  private static final double DEFAULT_OFFSET = 0;
  /** Default total data value in case the json is missing this parameter: {@value} */
  private static final double DEFAULT_TOTAL_DATA = 10;

  /** String with the name of the stream.distribution method that will be used for the node. */
  private String distributionMethod = DEFAULT_DISTRIBUTION_METHOD;
  /** Double with the value of the offset used for the stream.distribution method. */
  private double offset = DEFAULT_OFFSET;
  /** Double with the value of the total data used for the stream.distribution method. */
  private double totalData = DEFAULT_TOTAL_DATA;

  /**
   * Constructor.
   *
   * @param distributionMethod String with the name of the stream.distribution method that will be used for
   *     the node.
   * @param offset Double with the value of the offset used for the stream.distribution method.
   * @param totalData Double with the value of the total data used for the stream.distribution method.
   */
  public DistributionInfo(String distributionMethod, double offset, double totalData) {
    this.distributionMethod = distributionMethod;
    this.offset = offset;
    this.totalData = totalData;
  }

  /**
   * Getter of the stream.distribution method.
   *
   * @return A string with the name of the stream.distribution method used.
   */
  public String getDistributionMethod() {
    return this.distributionMethod;
  }

  /**
   * Getter of the Offset
   *
   * @return A double with the offset used in the stream.distribution.
   */
  public double getOffset() {
    return this.offset;
  }

  /**
   * Getter of the Total Data
   *
   * @return A double of the total data used in the stream.distribution.
   */
  public double getTotalData() {
    return this.totalData;
  }
}
