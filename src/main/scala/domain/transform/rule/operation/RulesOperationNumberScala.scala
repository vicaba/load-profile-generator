package domain.transform.rule.operation

import domain.value.ValueScala

final class RulesOperationNumberScala(operation: String, value: Double) extends RulesOperationTScala[Double] {
  override def applyChanges(output: ValueScala[Double]): ValueScala[Double] = operation match {
    case "+" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        output.value + this.value
      )

    case "-" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        output.value - this.value
      )

    case "*" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        output.value * this.value
      )

    case "/" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        output.value / this.value
      )

    case "%" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        output.value % this.value
      )

    case "=" =>
      new ValueScala[Double](
        output.id,
        output.`type`,
        output.name,
        this.value
      )

  }
}
