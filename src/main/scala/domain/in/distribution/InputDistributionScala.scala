package domain.in.distribution

import domain.in.condition.ConditionModifierScala

final class InputDistributionScala(id: String,
                                   condition: String,
                                   comparator: Int,
                                   result: ConditionModifierScala[_]) {

  def getId: String = this.id

  def getCondition: String = this.condition

  def getComparator: Int = this.comparator

  def getResult: ConditionModifierScala[_] = this.result

  def isDistribution(id: String): Boolean = result.getId == id
}
