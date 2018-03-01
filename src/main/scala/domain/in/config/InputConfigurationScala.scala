package domain.in.config

import domain.in.field.InputFieldScala
import domain.in.field.options.OptionsScala
import domain.in.rule.InputRuleScala

final case class InputConfigurationScala(fields: List[InputFieldScala[OptionsScala]],
                                         rules: List[InputRuleScala[_]]) {

  def getFields: List[InputFieldScala[OptionsScala]] = this.fields

  def getField(fieldIndex: Int): InputFieldScala[_] = this.fields(fieldIndex)

  def getRules: List[InputRuleScala[_]] = this.rules

  def getRule(ruleIndex: Int): InputRuleScala[_] = this.rules(ruleIndex)

}
