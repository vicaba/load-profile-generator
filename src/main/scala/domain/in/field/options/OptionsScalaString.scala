package domain.in.field.options

final case class OptionsScalaString(acceptedStrings: Seq[String]) extends OptionsScala {
  def getAcceptedStrings: Seq[String] = this.acceptedStrings
  def getAcceptedString(position: Int): String = this.acceptedStrings(position)
  def getSizeAccepted: Int = this.acceptedStrings.size

  override def toString: String = this.acceptedStrings.mkString(" - ")
}
