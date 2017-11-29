package domain.transform.rule.create;

import domain.in.rule.InputRule;
import domain.transform.rule.RulesCondition;
import domain.value.Value;

/**
 * Abstract class used to change the type of the right comparator to the left so RulesCondition has
 * both comparators with the same type, as the values grabbed from the inputField are by default
 * types that we can't work with.
 *
 * @param <LeftTermT> The type of the left comparator, that established the type of the right
 *     comparator.
 * @param <RightTermT> The type of the right comparator that we need to change.
 */
abstract class CreateRuleT<LeftTermT extends Comparable<? super LeftTermT>, RightTermT> {

  /**
   * Method that returns a rule condition that has both left and right comparator with the same
   * type.
   *
   * @param output The value that contains the left comparator we want to use for RulesCondition.
   * @param inputRule The InputRule that contains the right comparator and the condition.
   * @return Returns a RuleCondition with all the needed values set, with the right comparator
   *     having been changed to having the same type as the left one.
   */
  public RulesCondition<LeftTermT> getCondition(
      Value<LeftTermT> output, InputRule<RightTermT> inputRule) {
    LeftTermT leftCondition = output.getValue();
    RightTermT rightCondition = inputRule.getComparator();

    return new RulesCondition<>(
        leftCondition, inputRule.getCondition(), rightConditionValue(rightCondition));
  }

  /**
   * The method we use to change the type of right comparator to the left, that needs to be
   * established in each class that extends this one.
   *
   * @param rightCondition The right comparator that we want to change its type.
   * @return Returns the right comparator with the same type as the left comparator.
   */
  protected abstract LeftTermT rightConditionValue(RightTermT rightCondition);
}
