package domain.in.field.options

final class OptionsScalaString(acceptedStrings: List[String]) extends OptionsScala {
  def getAcceptedStrings: List[String] = this.acceptedStrings
  def getAcceptedString(position: Int): String = this.acceptedStrings(position)
  def getSizeAccepted: Int = this.acceptedStrings.size

}
