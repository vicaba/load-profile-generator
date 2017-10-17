import java.lang

import domain.in.field.InputField
import domain.in.field.options.OptionsNumber
import domain.transform.calculations.NumberEqualCalculations
import infrastructure.value.preparation.{NumberValueGeneration, ValueGeneration}

class SourceValueNumber(override val inputField: InputField[OptionsNumber]) extends SourceValueT[OptionsNumber, java.lang.Float, NumberEqualCalculations](inputField) {

  override protected var calculations: NumberEqualCalculations =
    new NumberEqualCalculations(inputField.getOptions.getRanges)

  override protected def dataGenerator: ValueGeneration[lang.Float, NumberEqualCalculations] =
    new NumberValueGeneration(inputField, calculations)
}
