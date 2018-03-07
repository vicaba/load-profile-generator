package domain.transform.rule.condition

import java.time.LocalDateTime

final class RulesConditionDateScala(leftCondition: LocalDateTime,
                                    condition: String,
                                    rightCondition: LocalDateTime) extends RulesConditionTScala[LocalDateTime] {

  override def checkResults: Boolean = this.condition match {
    case IsLessThan =>
      this.leftCondition.compareTo(this.rightCondition) < 0
    case IsEqualThan =>
      this.leftCondition.compareTo(this.rightCondition) == 0
    case IsGreaterThan =>
      this.leftCondition.compareTo(this.rightCondition) > 0
    case IsLessEqualThan =>
      this.leftCondition.compareTo(this.rightCondition) <= 0
    case IsGreaterEqualThan =>
      this.leftCondition.compareTo(this.rightCondition) >= 0
    case IsNotEqualThan =>
      this.leftCondition.compareTo(this.rightCondition) != 0
    case _ =>
      false

  }
}
