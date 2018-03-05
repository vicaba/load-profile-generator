package domain.transform.calculations.distribution

import domain.transform.calculations.CalculationsScala

trait DistributionCalculationsScala[T] extends CalculationsScala[T] {
  def increaseDistributionCounter(): Unit
}
