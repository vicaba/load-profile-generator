package output.rule;

public class ApplyRuleString extends ApplyRuleT<String, String> {
  @Override
  protected String rightConditionValue(String rightCondition) {
    return rightCondition;
  }
}
