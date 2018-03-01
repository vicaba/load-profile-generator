package domain.in.field.options

final case class OptionsScalaNumber(private val ranges: Seq[NumberRangeScala]) extends OptionsScala {
  def getRanges: Seq[NumberRangeScala] = this.ranges

}
