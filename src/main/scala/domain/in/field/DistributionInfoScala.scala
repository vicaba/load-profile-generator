package domain.in.field

final case class DistributionInfoScala(private val distributionMethod: String, private val offset: Double, private val totalData: Double) {
  def getDistributionMethod: String = this.distributionMethod

  def getOffset: Double = this.offset

  def getTotalData: Double = this.totalData
}
