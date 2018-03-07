package domain.transform.distributions

import domain.in.distribution.InputDistributionScala

final class DistributionsCheckScala(inputDistribution: Seq[InputDistributionScala]) {
  private final val DefaultInitialCounter = 0

  private var listCounters: Map[String, Int] = inputDistribution
    .map(dist => dist.id -> this.DefaultInitialCounter)
    .toMap

  def increaseAllCounters(): Unit = {
    this.listCounters = this.listCounters.map { case (key, value) => (key, value + 1) }

  }

  def resetCounter(): Unit = {
    this.listCounters = this.listCounters.map(counter => counter._1 -> this.DefaultInitialCounter)
  }

  def checkDistribution: Boolean = {
    this.inputDistribution
      .filter(dist => dist.condition == "count" && this.listCounters(dist.id) >= dist.comparator)
      .lengthCompare(this.listCounters.size) == 0
  }
}
