package domain.in.config

import domain.in.distribution.InputDistributionScala
import domain.in.field.InputFieldScala
import domain.in.field.options.OptionsScala
import domain.in.rule.InputRuleScala

final class InputConfigurationScala(fields: Seq[InputFieldScala[OptionsScala]],
                                         distributions: Seq[InputDistributionScala],
                                         rules: Seq[InputRuleScala[_]]) {

  def getFields: Seq[InputFieldScala[OptionsScala]] = this.fields

  def getField(fieldIndex: Int): InputFieldScala[_] = this.fields(fieldIndex)

  def getDistributions: Seq[InputDistributionScala] = this.distributions

  def getDistribution(distributionIndex: Int): InputDistributionScala = this.distributions(distributionIndex)

  def getRules: Seq[InputRuleScala[_]] = this.rules

  def getRule(ruleIndex: Int): InputRuleScala[_] = this.rules(ruleIndex)

  def isBroadcast(id: String): Boolean = {
    for (dist <- distributions) {
      if (dist.getId == id) return true
    }
    false
  }

  def isDistribution(id: String): Boolean = {
    for (dist <- distributions) {
      if (dist.isDistribution(id)) return true
    }
    false
  }

  def isDistributedBy(id: String): Seq[InputDistributionScala] = {
    distributions.filter(dist => dist.getResult.getId == id)
  }

}
