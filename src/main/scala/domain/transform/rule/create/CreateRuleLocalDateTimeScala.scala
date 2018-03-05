package domain.transform.rule.create

import java.time.LocalDateTime

import domain.in.rule.InputRuleScala
import domain.transform.rule.condition.RulesConditionDateScala
import domain.value.ValueScala

final class CreateRuleLocalDateTimeScala extends CreateRuleTScala[LocalDateTime, RulesConditionDateScala] {
  override def getCondition(output: ValueScala[LocalDateTime], inputRule: InputRuleScala[LocalDateTime]): RulesConditionDateScala = {
    new RulesConditionDateScala(output.getValue, inputRule.getCondition, inputRule.getComparator)

  }
}
