package domain.transform.rule

import java.time.LocalDateTime

import domain.in.condition.ConditionModifierScala
import domain.transform.rule.operation.{RulesOperationDateScala, RulesOperationNumberScala, RulesOperationStringScala}
import domain.value.ValueScala

import scala.collection.mutable.ListBuffer

final class RulesApplicationScala(condition: ConditionModifierScala[_]) {

  def applyRules(outputs: ListBuffer[ValueScala[_]]): Unit = {

    for (i <- outputs.indices) {
      outputs(i).id match {
        case this.condition.id =>
          //logger.debug("Changes will be made to output with value " + output.getValue)

          outputs(i).value match {
            case _: String =>
              val rulesOperation = new RulesOperationStringScala(condition.operation, condition.value.asInstanceOf[String])
              outputs(i) = rulesOperation.applyChanges(outputs(i).asInstanceOf[ValueScala[String]])

            case _: LocalDateTime =>
              val rulesOperation = new RulesOperationDateScala(condition.operation, condition.value.asInstanceOf[Long])
              outputs(i) = rulesOperation.applyChanges(outputs(i).asInstanceOf[ValueScala[LocalDateTime]])

            case _: Double =>
              val rulesOperation = new RulesOperationNumberScala(condition.operation, condition.value.asInstanceOf[Double])
              outputs(i) = rulesOperation.applyChanges(outputs(i).asInstanceOf[ValueScala[Double]])

            case _ =>
          }
        case _ =>
      }
    }

  }
}
