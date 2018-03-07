package domain.transform.rule.create

import domain.in.rule.InputRuleScala
import domain.transform.rule.condition.RulesConditionNumberScala
import domain.value.ValueScala

final class CreateRuleNumberScala extends CreateRuleTScala[Float, RulesConditionNumberScala] {
  override def getCondition(output: ValueScala[Float], inputRule: InputRuleScala[Float]): RulesConditionNumberScala = {
    new RulesConditionNumberScala(output.value, inputRule.condition, inputRule.comparator)
  }
}
