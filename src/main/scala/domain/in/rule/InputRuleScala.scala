package domain.in.rule

import domain.in.condition.ConditionModifierScala

final class InputRuleScala[T](private val id: String,
                                   private val condition: String,
                                   private val comparator: T,
                                   private val result: ConditionModifierScala[_]) {

  def getId: String = this.id

  def getCondition: String = this.condition

  def getComparator: T = this.comparator

  def getResult: ConditionModifierScala[_] = this.result
}
