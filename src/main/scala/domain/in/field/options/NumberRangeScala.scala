package domain.in.field.options

final class NumberRangeScala(private val min: Float = Float.MinValue, private val max: Float = Float.MaxValue) {
  def getMin: Float = this.min

  def getMax: Float = this.max
}
