package domain.transform.rule.operation

import java.time.LocalDateTime

import domain.value.ValueScala

final class RulesOperationDateScala(operation: String, value: BigInt) extends RulesOperationTScala[LocalDateTime] {
  override def applyChanges(output: ValueScala[LocalDateTime]): ValueScala[LocalDateTime] = operation match {
    case "+" =>
      new ValueScala[LocalDateTime](
        output.getId,
        output.getType,
        output.getName,
        output.getValue.plusSeconds(this.value.toLong)
      )

    case "-" =>
      new ValueScala[LocalDateTime](
        output.getId,
        output.getType,
        output.getName,
        output.getValue.minusSeconds(this.value.toLong)
      )

    case _ => null

  }
}
