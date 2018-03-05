package domain.value

final class ValueScala[T](id: String, `type`: String, name: String, value: T) {
  def getId: String = this.id
  def getType: String = this.`type`
  def getName: String = this.name
  def getValue: T = this.value
}
