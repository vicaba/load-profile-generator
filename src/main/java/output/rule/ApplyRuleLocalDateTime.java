package output.rule;

import java.time.LocalDateTime;

public class ApplyRuleLocalDateTime extends ApplyRuleT<LocalDateTime, LocalDateTime> {

  @Override
  protected LocalDateTime rightConditionValue(LocalDateTime rightCondition) {
    return rightCondition;
  }
}
