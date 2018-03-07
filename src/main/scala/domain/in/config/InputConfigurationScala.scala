package domain.in.config

import domain.in.distribution.InputDistributionScala
import domain.in.field.InputFieldScala
import domain.in.rule.InputRuleScala

final case class InputConfigurationScala(fields: Seq[InputFieldScala],
                                         distributions: Seq[InputDistributionScala],
                                         rules: Seq[InputRuleScala[_]]) {

  /*
  def getField(fieldIndex: Int): InputFieldScala = this.fields(fieldIndex)

  def getDistribution(distributionIndex: Int): InputDistributionScala = this.distributions(distributionIndex)

  def getRule(ruleIndex: Int): InputRuleScala[_] = this.rules(ruleIndex)
  */

  def isBroadcast(id: String): Boolean = {
    for (dist <- distributions) {
      if (dist.id == id) return true
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
    distributions.filter(dist => dist.result.id == id)
  }

}
