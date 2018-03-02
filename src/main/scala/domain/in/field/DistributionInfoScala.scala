package domain.in.field

final class DistributionInfoScala(distributionMethod: String, offset: Double, totalData: Double) {
  def getDistributionMethod: String = this.distributionMethod

  def getOffset: Double = this.offset

  def getTotalData: Double = this.totalData
}
