package domain.in.field

import domain.in.field.options.OptionsScala

final case class InputFieldScala(private val info: InputFieldInfoScala, private val options: OptionsScala, private val distributionInfo: DistributionInfoScala) {
  def getInfo: InputFieldInfoScala = this.info

  def getOptions: OptionsScala = this.options

  def getDistributionInfo: DistributionInfoScala = this.distributionInfo
}
