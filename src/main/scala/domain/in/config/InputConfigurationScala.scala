package domain.in.config

import domain.in.distribution.InputDistributionScala
import domain.in.field.InputFieldInfoScala
import domain.in.rule.InputRuleScala

final class InputConfigurationScala[T](fields: List[InputFieldInfoScala], rules: List[InputRuleScala[T]], distributions: List[InputDistributionScala[T]]) {
  def getFields: List[InputFieldInfoScala] = this.fields
  def getRules: List[InputRuleScala[T]] = this.rules
  def getDistributions: List[InputDistributionScala[T]] = this.distributions

  def getField(fieldIndex: Int): InputFieldInfoScala = this.fields(fieldIndex)
  def getRules(ruleIndex: Int): InputRuleScala[T] = this.rules(ruleIndex)
  def getDistribution(distributionIndex: Int): InputDistributionScala[T] = this.distributions(distributionIndex)

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

  def isDistributedBy(id: String): List[InputDistributionScala[T]] = {
    this.distributions.filter(dist => dist.getId == id)
  }
}
