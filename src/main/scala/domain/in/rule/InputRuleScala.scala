package domain.in.rule

import domain.in.condition.ConditionModifierScala

final case class InputRuleScala[T](id: String,
                                   condition: String,
                                   comparator: T,
                                   result: ConditionModifierScala[_])