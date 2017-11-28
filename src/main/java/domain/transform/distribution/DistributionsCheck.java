package domain.transform.distribution;

import domain.in.distribution.InputDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that is used to check and work with the distribution condition of the DistributionFlow.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class DistributionsCheck {
  /** Constant with the default initial value for the counter: {@value} */
  private static final int DEFAULT_INITIAL_COUNTER = 0;

  /** List with all the InputDistribution fields of the nodes that are connected to this node. */
  private List<InputDistribution> inputDistribution;
  /** HashMap used to make it easier to obtain a counter based on the id of a node. */
  private Map<String, Integer> listCounters = new HashMap<>();

  /**
   * Constructor.
   *
   * @param inputDistribution List of InputDistribution fields, one for each node that affects the
   *     DistributionFlow.
   */
  public DistributionsCheck(List<InputDistribution> inputDistribution) {
    this.inputDistribution = inputDistribution;
    for (InputDistribution input : inputDistribution) {
      this.listCounters.put(input.getId(), DEFAULT_INITIAL_COUNTER);
    }
  }

  /** Method that increases all counters inside listCounters by one. */
  public void increaseAllCounters() {
    for (Map.Entry<String, Integer> entry : this.listCounters.entrySet()) {
      this.listCounters.put(entry.getKey(), entry.getValue() + 1);
    }
  }

  /** Method that resets all counters inside listCounters back to the initial default value. */
  public void resetCounter() {
    for (Map.Entry<String, Integer> entry : this.listCounters.entrySet()) {
      this.listCounters.put(entry.getKey(), DEFAULT_INITIAL_COUNTER);
    }
  }

  /**
   * Method that checks if the distribution condition is fulfilled.
   *
   * @return Returns true if all nodes that distribute this node fulfill the condition, and false if
   *     at least one doesn't.
   */
  public boolean checkDistribution() {
    int totalOkay = 0;
    for (InputDistribution input : this.inputDistribution) {
      switch (input.getCondition()) {
        case "count":
          if (this.listCounters.get(input.getId()) >= input.getComparator()) {
            totalOkay++;
          }
          break;
        default:
          break;
      }
    }

    return totalOkay == this.listCounters.size();
  }
}
