package domain.in.field.options

final case class OptionsScalaDate(startingDate: String, timeIncrement: Int) extends OptionsScala {

  override def toString: String = this.startingDate + " - " + this.timeIncrement
}
