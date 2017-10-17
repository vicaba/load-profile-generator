import java.time.LocalDateTime

import domain.in.field.InputField
import domain.in.field.options.OptionsDate
import domain.transform.calculations.DateEqualCalculations
import infrastructure.value.preparation.{DateValueGeneration, ValueGeneration}

class SourceValueDate(override val inputField: InputField[OptionsDate])
  extends SourceValueT[OptionsDate, LocalDateTime, DateEqualCalculations](inputField) {

  override protected def dataGenerator: ValueGeneration[LocalDateTime, DateEqualCalculations] =
    new DateValueGeneration(inputField, calculations)

  override protected var calculations: DateEqualCalculations =
    new DateEqualCalculations(inputField.getOptions.getStartingDate, inputField.getOptions.getTimeIncrement)
}
