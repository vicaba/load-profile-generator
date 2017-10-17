package domain.transform.rule.create;

public class CreateRuleNumber extends CreateRuleT<Float, Double> {

  @Override
  protected Float rightConditionValue(Double rightCondition) {
    return rightCondition.floatValue();
  }
}
