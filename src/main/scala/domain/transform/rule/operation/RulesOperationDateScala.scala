package domain.transform.rule.operation

import java.time.LocalDateTime

import domain.value.ValueScala

final class RulesOperationDateScala(operation: String, value: Long) extends RulesOperationTScala[LocalDateTime] {
  override def applyChanges(output: ValueScala[LocalDateTime]): ValueScala[LocalDateTime] = operation match {
    case "+" =>
      new ValueScala[LocalDateTime](
        output.id,
        output.`type`,
        output.name,
        output.value.plusSeconds(this.value.toLong)
      )

    case "-" =>
      new ValueScala[LocalDateTime](
        output.id,
        output.`type`,
        output.name,
        output.value.minusSeconds(this.value.toLong)
      )

    case _ => null

  }
}
