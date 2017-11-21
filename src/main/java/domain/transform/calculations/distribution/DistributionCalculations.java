package domain.transform.calculations.distribution;

import domain.transform.calculations.Calculations;

public interface DistributionCalculations<T> extends Calculations<T> {
  void increaseDistributionCounter();
}
