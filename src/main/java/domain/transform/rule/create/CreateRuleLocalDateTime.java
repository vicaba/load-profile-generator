package domain.transform.rule.create;

import java.time.LocalDateTime;

// TODO This class is redundant, you don't need to change LDT to LDT. Consider removing it.

/** Class used to change the type of the right comparator to String. */
public class CreateRuleLocalDateTime extends CreateRuleT<LocalDateTime, LocalDateTime> {

  /**
   * Method used to change the type of the right comparator to LocalDateTime.
   *
   * @param rightCondition The right comparator that we want to change its type.
   * @return Returns the right comparator as a LocalDateTime.
   */
  @Override
  protected LocalDateTime rightConditionValue(LocalDateTime rightCondition) {
    return rightCondition;
  }
}
