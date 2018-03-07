package domain.transform.rule.operation

import domain.value.ValueScala

final class RulesOperationStringScala(operation: String, value: String) extends RulesOperationTScala[String] {
  override def applyChanges(output: ValueScala[String]): ValueScala[String] = this.operation match {
    case "+S" =>
      new ValueScala[String](
        output.id,
        output.`type`,
        output.name,
        output.value + this.value
      )

    case "S+" =>
      new ValueScala[String](
        output.id,
        output.`type`,
        output.name,
        this.value + output.value
      )

    case "=" =>
      new ValueScala[String](
        output.id,
        output.`type`,
        output.name,
        this.value
      )

    case _ => null

  }
}
