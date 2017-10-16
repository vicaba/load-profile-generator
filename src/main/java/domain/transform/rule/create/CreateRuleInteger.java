package domain.transform.rule.create;

public class CreateRuleInteger extends CreateRuleT<Integer, Double> {

  @Override
  protected Integer rightConditionValue(Double rightCondition) {
    return rightCondition.intValue();
  }
}
