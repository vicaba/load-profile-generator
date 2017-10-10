package output.rule;

public class ApplyRuleInteger extends ApplyRuleT<Integer, Double> {
  @Override
  protected Integer rightConditionValue(Double rightCondition) {
    return rightCondition.intValue();
  }
}
