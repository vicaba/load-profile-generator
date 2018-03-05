package domain.in.field

import domain.in.field.options.OptionsScala

final class InputFieldScala[T <: OptionsScala](info: InputFieldInfoScala, options: T, distributionInfo: DistributionInfoScala) {
  def getName: String = this.info.getName

  def getType: String = this.info.getType

  def getId: String = this.info.getId

  def getInfo: InputFieldInfoScala = this.info

  def getOptions: T = this.options

  def getDistributionInfo: DistributionInfoScala = this.distributionInfo
}
