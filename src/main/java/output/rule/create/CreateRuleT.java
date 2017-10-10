package output.rule.create;

import domain.output.Output;
import domain.rules.InputRule;
import output.rule.RulesCondition;

abstract class CreateRuleT<LeftTermT extends Comparable<? super LeftTermT>, RightTermT> {

  public RulesCondition<LeftTermT> getCondition(
      Output<LeftTermT> output, InputRule<RightTermT> inputRule) {
    LeftTermT leftCondition = output.getValue();
    RightTermT rightCondition = inputRule.getComparator();

    return new RulesCondition<>(
        leftCondition, inputRule.getCondition(), rightConditionValue(rightCondition));
  }

  protected abstract LeftTermT rightConditionValue(RightTermT rightCondition);
}
