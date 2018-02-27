package domain.in.field.options

final class NumberRangeScala(min: Float = Float.MinValue, max: Float = Float.MaxValue) {
  def getMin: Float = this.min
  def getMax: Float = this.max
}
