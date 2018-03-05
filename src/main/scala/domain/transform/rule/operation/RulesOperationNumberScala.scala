package domain.transform.rule.operation

import domain.value.ValueScala

final class RulesOperationNumberScala(operation: String, value: Float) extends RulesOperationTScala[Float] {
  override def applyChanges(output: ValueScala[Float]): ValueScala[Float] = operation match {
    case "+" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        output.getValue + this.value
      )

    case "-" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        output.getValue - this.value
      )

    case "*" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        output.getValue * this.value
      )

    case "/" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        output.getValue / this.value
      )

    case "%" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        output.getValue % this.value
      )

    case "=" =>
      new ValueScala[Float](
        output.getId,
        output.getType,
        output.getName,
        this.value
      )

  }
}
