package domain.transform.rule.condition

trait RulesConditionTScala[T] {
  protected final val IsLessThan = "isLessThan"
  protected final val IsGreaterThan = "isGreaterThan"
  protected final val IsEqualThan = "isEqualThan"
  protected final val IsLessEqualThan = "isLessEqualThan"
  protected final val IsGreaterEqualThan = "isGreaterEqualThan"
  protected final val IsNotEqualThan = "isNotEqualThan"

  def checkResults: Boolean
}
