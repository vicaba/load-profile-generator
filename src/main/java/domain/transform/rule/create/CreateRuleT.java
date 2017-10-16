package domain.transform.rule.create;

import domain.value.Value;
import domain.in.rule.InputRule;
import domain.transform.rule.RulesCondition;

abstract class CreateRuleT<LeftTermT extends Comparable<? super LeftTermT>, RightTermT> {

  public RulesCondition<LeftTermT> getCondition(
      Value<LeftTermT> output, InputRule<RightTermT> inputRule) {
    LeftTermT leftCondition = output.getValue();
    RightTermT rightCondition = inputRule.getComparator();

    return new RulesCondition<>(
        leftCondition, inputRule.getCondition(), rightConditionValue(rightCondition));
  }

  protected abstract LeftTermT rightConditionValue(RightTermT rightCondition);
}
