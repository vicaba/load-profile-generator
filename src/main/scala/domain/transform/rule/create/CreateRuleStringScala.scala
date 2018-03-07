package domain.transform.rule.create

import domain.in.rule.InputRuleScala
import domain.transform.rule.condition.RulesConditionStringScala
import domain.value.ValueScala

final class CreateRuleStringScala extends CreateRuleTScala[String, RulesConditionStringScala] {
  override def getCondition(output: ValueScala[String], inputRule: InputRuleScala[String]): RulesConditionStringScala = {
    new RulesConditionStringScala(output.value, inputRule.condition, inputRule.comparator)
  }
}
