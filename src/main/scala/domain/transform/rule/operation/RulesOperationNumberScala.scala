package domain.transform.rule.operation

import domain.value.ValueScala

final class RulesOperationNumberScala(operation: String, value: Float) extends RulesOperationTScala[Float] {
  override def applyChanges(output: ValueScala[Float]): ValueScala[Float] = operation match {
    case "+" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        output.value + this.value
      )

    case "-" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        output.value - this.value
      )

    case "*" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        output.value * this.value
      )

    case "/" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        output.value / this.value
      )

    case "%" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        output.value % this.value
      )

    case "=" =>
      new ValueScala[Float](
        output.id,
        output.`type`,
        output.name,
        this.value
      )

  }
}
