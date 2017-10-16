package domain.transform.rule.create;

import java.time.LocalDateTime;

public class CreateRuleLocalDateTime extends CreateRuleT<LocalDateTime, LocalDateTime> {

  @Override
  protected LocalDateTime rightConditionValue(LocalDateTime rightCondition) {
    return rightCondition;
  }
}
