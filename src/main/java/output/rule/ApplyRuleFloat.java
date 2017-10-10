package output.rule;

public class ApplyRuleFloat extends ApplyRuleT<Float, Double> {

  @Override
  protected Float rightConditionValue(Double rightCondition) {
    return rightCondition.floatValue();
  }
}
