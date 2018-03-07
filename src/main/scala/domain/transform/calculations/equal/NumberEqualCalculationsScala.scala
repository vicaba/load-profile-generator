package domain.transform.calculations.equal

import java.util.concurrent.ThreadLocalRandom

import domain.in.field.options.NumberRangeScala

final class NumberEqualCalculationsScala(numberRanges: Seq[NumberRangeScala]) extends EqualCalculationsScala[Double] {
  override def calculate: Double = {
    val numberRange = this.numberRanges(ThreadLocalRandom.current.nextInt(0, this.numberRanges.size))

    val fMin = numberRange.min
    ThreadLocalRandom.current.nextFloat * (numberRange.max - fMin) + fMin
  }
}
