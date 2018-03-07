package domain.in.distribution

import domain.in.condition.ConditionModifierScala

final case class InputDistributionScala(id: String,
                                        condition: String,
                                        comparator: Int,
                                        result: ConditionModifierScala[_]) {

  def isDistribution(id: String): Boolean = result.id == id
}
