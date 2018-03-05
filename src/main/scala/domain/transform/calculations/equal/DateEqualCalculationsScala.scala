package domain.transform.calculations.equal

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

final class DateEqualCalculationsScala(startingDate:String, timeIncrement: Int) extends EqualCalculationsScala[LocalDateTime] {
  private final val DateFormat = "dd-MM-yyyy HH:mm"

  private var CurrentDate = LocalDateTime.parse(startingDate, DateTimeFormatter.ofPattern(this.DateFormat))

  override def calculate: LocalDateTime = {
    this.CurrentDate = this.CurrentDate.plusSeconds(this.timeIncrement)
    this.CurrentDate
  }
}
