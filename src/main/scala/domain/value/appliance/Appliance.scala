package domain.value.appliance

import domain.in.field.options.NumberRangeScala

final class Appliance(name: String,
                      duration: Int,
                      energy: Int,
                      timeIntervals: Seq[NumberRangeScala]) {
  def getName: String = this.name

  def getDuration: Int = this.duration

  def getEnergy: Int = this.energy

  def getTotalEnergyConsumed: Int = this.duration * this.energy

  def getTimeInterval: Seq[NumberRangeScala] = this.timeIntervals

  def getTimeInterval(indexTime: Int): NumberRangeScala = this.timeIntervals(indexTime)

  override def toString: String = {
    "N:"+this.name+" D:"+this.duration+" E:"+this.energy+" T:m("+this.timeIntervals.head.getMin+")M("+this.timeIntervals.head.getMax+")"
  }
}
