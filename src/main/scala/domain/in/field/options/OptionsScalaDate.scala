package domain.in.field.options

final case class OptionsScalaDate(private val startingDate: String, private val timeIncrement: Int) extends OptionsScala {
  def getStartingDate: String = this.startingDate
  def getTimeIncrement: Int = this.timeIncrement

  override def toString: String = this.startingDate + " - " + this.timeIncrement
}
