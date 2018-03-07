package domain.value.appliance

import domain.in.field.options.NumberRangeScala

final case class Appliance(name: String,
                           duration: Int,
                           energy: Int,
                           timeIntervals: Seq[NumberRangeScala]) {

  def calculateTotalEnergyConsumed: Int = this.duration * this.energy

  def getTimeInterval(indexTime: Int): NumberRangeScala = this.timeIntervals(indexTime)

  override def toString: String = {
    "N:" + this.name + " D:" + this.duration + " E:" + this.energy + " T:m(" + this.timeIntervals.head.min + ")M(" + this.timeIntervals.head.max + ")"
  }
}
