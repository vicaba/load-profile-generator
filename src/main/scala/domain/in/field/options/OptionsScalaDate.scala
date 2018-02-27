package domain.in.field.options

final class OptionsScalaDate(startingDate: String = "01/01/1970", timeIncrement: Int = 1) extends OptionsScala {
  def getStartingDate: String = this.startingDate
  def getTimeIncrement: Int = this.timeIncrement

}
