package domain.in.field.options

final case class OptionsScalaDate(startingDate: String, timeIncrement: Int) extends OptionsScala {
  def getStartingDate: String = this.startingDate
  def getTimeIncrement: Int = this.timeIncrement

  override def toString: String = this.startingDate + " - " + this.timeIncrement
}
