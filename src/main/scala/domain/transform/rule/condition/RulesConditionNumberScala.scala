package domain.transform.rule.condition

final class RulesConditionNumberScala(leftCondition: Double,
                                      condition: String,
                                      rightCondition: Double) extends RulesConditionTScala[Double] {

  override def checkResults: Boolean = this.condition match {
    case IsLessThan =>
      this.leftCondition < this.rightCondition
    case IsEqualThan =>
      this.leftCondition == this.rightCondition
    case IsGreaterThan =>
      this.leftCondition > this.rightCondition
    case IsLessEqualThan =>
      this.leftCondition <= this.rightCondition
    case IsGreaterEqualThan =>
      this.leftCondition >= this.rightCondition
    case IsNotEqualThan =>
      this.leftCondition != this.rightCondition
    case _ =>
      false

  }
}
