package domain.in.field.options

final class OptionsScalaNumber(ranges: List[NumberRangeScala]) extends OptionsScala {
  def getRanges: List[NumberRangeScala] = this.ranges
}
