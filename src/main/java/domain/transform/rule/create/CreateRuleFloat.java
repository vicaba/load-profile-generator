package domain.transform.rule.create;

public class CreateRuleFloat extends CreateRuleT<Float, Double> {

  @Override
  protected Float rightConditionValue(Double rightCondition) {
    return rightCondition.floatValue();
  }
}
