package domain.in.condition

final case class ConditionModifierScala[T](private val id: String,
                                           private val operation: String,
                                           private val value: T) {
  def getId: String = this.id

  def getOperation: String = this.operation

  def getValue: T = this.value

}
