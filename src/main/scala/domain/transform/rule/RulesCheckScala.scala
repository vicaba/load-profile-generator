package domain.transform.rule

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import domain.in.condition.ConditionModifierScala
import domain.in.rule.InputRuleScala
import domain.transform.rule.create.{CreateRuleLocalDateTimeScala, CreateRuleNumberScala, CreateRuleStringScala}
import domain.value.ValueScala

final class RulesCheckScala(rules: Seq[InputRuleScala[_]]) {

  def applyRules(outputs: Seq[ValueScala[_]]): Seq[ValueScala[_]] = {
    for (rule <- this.rules) {
      for (output <- outputs) {
        if (rule.getId == output.getId) {
          var resultsChecked = false

          output.getValue match {
            case _: Float => resultsChecked = (new CreateRuleNumberScala)
              .getCondition(output.asInstanceOf[ValueScala[Float]], rule.asInstanceOf[InputRuleScala[Float]])
              .checkResults

            case _: String => resultsChecked = (new CreateRuleStringScala)
              .getCondition(output.asInstanceOf[ValueScala[String]], rule.asInstanceOf[InputRuleScala[String]])
              .checkResults

            case _: LocalDateTime =>
              //TODO This code below is disgusting and should be changed in a near future
              val dateRule = new InputRuleScala(
                rule.getId,
                rule.getCondition,
                LocalDateTime.parse(rule.getComparator.asInstanceOf[String], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                rule.getResult.asInstanceOf[ConditionModifierScala[Float]]
              )

              resultsChecked = (new CreateRuleLocalDateTimeScala)
                .getCondition(output.asInstanceOf[ValueScala[LocalDateTime]], dateRule)
                .checkResults

            case _ =>
          }
          /* If the value fulfills a condition specified by the rule, it wil apply the changes. */ if (resultsChecked) {
            val rulesApplication = new RulesApplicationScala(rule.getId, rule.getResult)
            rulesApplication.applyRules(outputs)
          }
        }
      }
    }
    outputs
  }

}
