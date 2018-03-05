package domain.transform.rule.operation

import domain.value.ValueScala

final class RulesOperationStringScala(operation: String, value: String) extends RulesOperationTScala[String] {
  override def applyChanges(output: ValueScala[String]): ValueScala[String] = this.operation match {
    case "+S" =>
      new ValueScala[String](
        output.getId,
        output.getType,
        output.getName,
        output.getValue + this.value
      )

    case "S+" =>
      new ValueScala[String](
        output.getId,
        output.getType,
        output.getName,
        this.value + output.getValue
      )

    case "=" =>
      new ValueScala[String](
        output.getId,
        output.getType,
        output.getName,
        this.value
      )

    case _ => null

  }
}
