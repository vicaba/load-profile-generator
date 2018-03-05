package domain.in.field

import domain.in.field.options.OptionsScala

final class InputFieldScala[T](info: InputFieldInfoScala, options: OptionsScala, distributionInfo: DistributionInfoScala) {
  def getName: String = this.info.getName

  def getType: String = this.info.getType

  def getId: String = this.info.getId

  def getInfo: InputFieldInfoScala = this.info

  def getOptions: OptionsScala = this.options

  def getDistributionInfo: DistributionInfoScala = this.distributionInfo
}
