package domain.transform.distribution;

import domain.in.distribution.InputDistribution;

public class DistributionsCheck {
  private InputDistribution inputDistribution;

  public DistributionsCheck(InputDistribution inputDistribution) {
    this.inputDistribution = inputDistribution;
  }

  public boolean checkDistribution(int value) {
    switch (this.inputDistribution.getCondition()) {
      case "count":
        if (value >= this.inputDistribution.getComparator()) return true;
        break;

      default:
        break;
    }
    return false;
  }
}
