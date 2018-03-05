package domain.transform.rule.operation

import domain.value.ValueScala

trait RulesOperationTScala[T] {
  def applyChanges(output: ValueScala[T]): ValueScala[T]
}
