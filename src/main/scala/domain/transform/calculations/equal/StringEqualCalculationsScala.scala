package domain.transform.calculations.equal

import java.util.concurrent.ThreadLocalRandom

final class StringEqualCalculationsScala(acceptedStrings: Seq[String]) extends EqualCalculationsScala[String] {
  override def calculate: String = {
    val randomValue = ThreadLocalRandom.current.nextInt(0, this.acceptedStrings.size)
    this.acceptedStrings(randomValue)
  }
}
