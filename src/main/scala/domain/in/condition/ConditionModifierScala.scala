package domain.in.condition

final class ConditionModifierScala[T](id: String,
                                      operation: String,
                                      value: T) {
  def getId: String = this.id

  def getOperation: String = this.operation

  def getValue: T = this.value

}
