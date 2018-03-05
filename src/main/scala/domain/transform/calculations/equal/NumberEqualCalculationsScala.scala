package domain.transform.calculations.equal

import java.util.concurrent.ThreadLocalRandom

import domain.in.field.options.NumberRangeScala

final class NumberEqualCalculationsScala(numberRanges: Seq[NumberRangeScala]) extends EqualCalculationsScala[Float] {
  override def calculate: Float = {
    val numberRange = this.numberRanges(ThreadLocalRandom.current.nextInt(0, this.numberRanges.size))

    val fMin = numberRange.getMin
    ThreadLocalRandom.current.nextFloat * (numberRange.getMax - fMin) + fMin
  }
}
