package domain.transform.rule.create;

// TODO This class is redundant, you don't need to change string to string. Consider removing it.

/** Class used to change the type of the right comparator to String. */
public class CreateRuleString extends CreateRuleT<String, String> {

  /**
   * Changes the type of the right comparator to string.
   *
   * @param rightCondition The right comparator that we want to change its type.
   * @return Returns the right comparator, but with the type of the left comparator.
   */
  @Override
  protected String rightConditionValue(String rightCondition) {
    return rightCondition;
  }
}
