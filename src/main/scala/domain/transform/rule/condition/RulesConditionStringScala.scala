package domain.transform.rule.condition

final class RulesConditionStringScala(leftCondition: String,
                                      condition: String,
                                      rightCondition: String) extends RulesConditionTScala[String] {

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
