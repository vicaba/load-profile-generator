import domain.in.field.InputField
import domain.in.field.options.OptionsString
import domain.transform.calculations.StringEqualCalculations
import infrastructure.value.preparation.{StringValueGeneration, ValueGeneration}

class SourceValueString(override val inputField: InputField[OptionsString])
  extends SourceValueT[OptionsString, String, StringEqualCalculations](inputField) {

  override protected var calculations: StringEqualCalculations =
    new StringEqualCalculations(inputField.getOptions.getAcceptedStrings)

  override protected def dataGenerator: ValueGeneration[String, StringEqualCalculations] =
    new StringValueGeneration(inputField, calculations)
}
