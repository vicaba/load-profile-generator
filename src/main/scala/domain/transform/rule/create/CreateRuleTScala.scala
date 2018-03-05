package domain.transform.rule.create

import domain.in.rule.InputRuleScala
import domain.transform.rule.condition.RulesConditionTScala
import domain.value.ValueScala

trait CreateRuleTScala[T, S <: RulesConditionTScala[T]] {

  def getCondition(output: ValueScala[T], inputRule: InputRuleScala[T]): S
}
