package domain.in.rule

import domain.in.condition.ConditionModifierScala

final case class InputRuleScala[T](private val id: String,
                                   private val condition: String,
                                   private val comparator: T,
                                   private val result: ConditionModifierScala[T]) {

  def getId: String = this.id

  def getCondition: String = this.condition

  def getComparator: T = this.comparator

  def getResult: ConditionModifierScala[T] = this.result
}
