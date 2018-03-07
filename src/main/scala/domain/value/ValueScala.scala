package domain.value

final case class ValueScala[T](id: String, `type`: String, name: String, value: T)