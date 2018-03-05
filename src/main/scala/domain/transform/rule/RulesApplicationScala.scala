package domain.transform.rule

import java.time.LocalDateTime

import domain.in.condition.ConditionModifierScala
import domain.transform.rule.operation.{RulesOperationDateScala, RulesOperationNumberScala, RulesOperationStringScala}
import domain.value.ValueScala

final class RulesApplicationScala(id: String, condition: ConditionModifierScala[_]) {

  def applyRules(outputs: Seq[ValueScala[_]]): Seq[ValueScala[_]] = {

    outputs
      .map(out => out.getId match {
        case this.id =>
          //logger.debug("Changes will be made to output with value " + output.getValue)

          out.getValue match {
            case _: String =>
              val rulesOperation = new RulesOperationStringScala(condition.getOperation, condition.getValue.asInstanceOf[String])
              rulesOperation.applyChanges(out.asInstanceOf[ValueScala[String]])

            case _: LocalDateTime =>
              print(condition.getValue)
              val rulesOperation = new RulesOperationDateScala(condition.getOperation, condition.getValue.asInstanceOf[BigInt])
              rulesOperation.applyChanges(out.asInstanceOf[ValueScala[LocalDateTime]])

            case _: Float =>
              val rulesOperation = new RulesOperationNumberScala(condition.getOperation, condition.getValue.asInstanceOf[Float])
              rulesOperation.applyChanges(out.asInstanceOf[ValueScala[Float]])

            case _ =>
          }
        case _ => out
      }

      ).asInstanceOf[Seq[ValueScala[_]]]


  }
}
