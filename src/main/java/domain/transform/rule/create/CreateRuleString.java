package domain.transform.rule.create;

public class CreateRuleString extends CreateRuleT<String, String> {

  @Override
  protected String rightConditionValue(String rightCondition) {
    return rightCondition;
  }
}
