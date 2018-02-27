package domain.in.field

import domain.in.field.options.OptionsScala

final class InputFieldScala[T](field: InputFieldInfoScala, opt: OptionsScala, distributionInfo: DistributionInfoScala) {
  def getId: String = this.field.getId
  def getName: String = this.field.getName
  def getType: String = this.field.getType
  def getOptions: OptionsScala = this.opt
  def getDistributionInfo: DistributionInfoScala = this.distributionInfo

}
