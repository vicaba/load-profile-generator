package domain.transform.rule

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import domain.in.rule.InputRuleScala
import domain.transform.rule.create.{CreateRuleLocalDateTimeScala, CreateRuleNumberScala, CreateRuleStringScala}
import domain.value.ValueScala

import scala.collection.mutable.ListBuffer

final class RulesCheckScala(rules: Seq[InputRuleScala[_]]) {

  def applyRules(outputs: Seq[ValueScala[_]]): Seq[ValueScala[_]] = {
    val outMutable = outputs.to[ListBuffer]

    for (rule <- this.rules) {
      for (output <- outMutable) {
        if (rule.id == output.id) {
          var resultsChecked = false

          output.value match {
            case _: Double =>
              resultsChecked = (new CreateRuleNumberScala)
                .getCondition(output.asInstanceOf[ValueScala[Double]], rule.asInstanceOf[InputRuleScala[Double]])
                .checkResults

            case _: String => resultsChecked = (new CreateRuleStringScala)
              .getCondition(output.asInstanceOf[ValueScala[String]], rule.asInstanceOf[InputRuleScala[String]])
              .checkResults

            case _: LocalDateTime =>
              //TODO This code below is disgusting and should be changed in a near future
              val dateRule = InputRuleScala(
                rule.id,
                rule.condition,
                LocalDateTime.parse(rule.comparator.asInstanceOf[String], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                rule.result
              )

              resultsChecked = (new CreateRuleLocalDateTimeScala)
                .getCondition(output.asInstanceOf[ValueScala[LocalDateTime]], dateRule)
                .checkResults

            case _ =>
          }
          /* If the value fulfills a condition specified by the rule, it wil apply the changes. */
          if (resultsChecked) {
            val rulesApplication = new RulesApplicationScala(rule.result)
            rulesApplication.applyRules(outMutable)
          }
        }
      }
    }

    outMutable.toList
  }

}
