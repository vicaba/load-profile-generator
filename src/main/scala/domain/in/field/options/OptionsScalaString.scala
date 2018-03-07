package domain.in.field.options

final case class OptionsScalaString(acceptedStrings: Seq[String]) extends OptionsScala {

  override def toString: String = this.acceptedStrings.mkString(" - ")
}
