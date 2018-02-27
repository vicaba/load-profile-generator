package domain.in.rule

import domain.in.condition.ConditionModifierScala

final class InputRuleScala[T](id: String, condition: String, comparator: T, result: ConditionModifierScala[T]) {
  def getId: String = this.id
  def getCondition: String = this.condition
  def getComparator: T = this.comparator
  def getResult: ConditionModifierScala[T] = this.result
}
