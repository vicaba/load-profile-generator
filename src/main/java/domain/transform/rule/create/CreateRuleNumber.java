package domain.transform.rule.create;

/**
 * Class used to change the type of the right comparator from double to float.
 */
public class CreateRuleNumber extends CreateRuleT<Float, Double> {

  /**
   * Method used to change right comparator from double to float.
   * @param rightCondition The right comparator that we want to change its type.
   * @return Returns right comparator as a float.
   */
  @Override
  protected Float rightConditionValue(Double rightCondition) {
    return rightCondition.floatValue();
  }
}
