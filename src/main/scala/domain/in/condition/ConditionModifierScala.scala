package domain.in.condition

final case class ConditionModifierScala[T](id: String, operation: String, value: T)