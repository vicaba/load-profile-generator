package domain.in.distribution;

import domain.in.condition.ConditionModifier;

/**
 * Class that contains the information that is needed for nodes to distribute.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class InputDistribution {
  /** Id of the node that will follow the stream.distribution. */
  private String id;
  /** Condition that will be used to check if the stream.distribution applies. */
  private String condition;
  /** Right comparator that will be used for the condition. */
  private int comparator;
  /**
   * Info of the kind of operation that will be applied if the stream.distribution condition is fulfilled.
   */
  private ConditionModifier result;

  /**
   * Constructor.
   *
   * @param id Id of the node that we will be used to affect the node that will be distributed.
   * @param condition String with the condition that will be applied to the node.
   * @param comparator Integer with the right comparator for the condition.
   * @param result The kind of operation that will be applied if the stream.distribution condition is
   *     fulfilled.
   */
  public InputDistribution(String id, String condition, int comparator, ConditionModifier result) {
    this.id = id;
    this.condition = condition;
    this.comparator = comparator;
    this.result = result;
  }

  /**
   * Getter of the Id
   *
   * @return A String with the id of the node.
   */
  public String getId() {
    return id;
  }

  /**
   * Getter of the condition.
   *
   * @return A String with the condition that is being used for the node.
   */
  public String getCondition() {
    return condition;
  }

  /**
   * Getter of the right comparator.
   *
   * @return An Integer with the right comparator used for the condition.
   */
  public int getComparator() {
    return comparator;
  }

  /**
   * Getter of the Condition modifier.
   *
   * @return A ConditionModifier object with the info of the changes that will be made if the
   *     condition is fulfilled.
   */
  public ConditionModifier getResult() {
    return result;
  }

  /**
   * Returns if the id passed is the same as the id of this object, meaning this node must
   * distribute another node.
   *
   * @param id String with the id of the node we're checking.
   * @return Returns true if the id is the same, and false if it isn't.
   */
  public boolean isDistribution(String id) {
    return result.getId().equals(id);
  }
}
