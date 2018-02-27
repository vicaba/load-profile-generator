package domain.in.distribution

import domain.in.condition.ConditionModifierScala

final class InputDistributionScala[T](id: String, condition: String, comparator: Int, result: ConditionModifierScala[T]) {
  def getId: String = this.id
  def getCondition: String = this.condition
  def getComparator: Int = this.comparator
  def getResult: ConditionModifierScala[T] = this.result
  def isDistribution(id: String): Boolean = result.getId == id
}
