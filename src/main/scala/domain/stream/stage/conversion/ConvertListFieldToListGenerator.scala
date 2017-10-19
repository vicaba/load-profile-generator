package domain.stream.stage.conversion

import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.transform.calculations.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

class ConvertListFieldToListGenerator(val in: List[InputField[_]])
  extends ConvertListXToListY[InputField[_], ValueGenerator[_, _]] {

  override def convert(): List[ValueGenerator[_, _]] = {
    in.map(field => field.getOptions match {
      case _: OptionsString =>
        new StringValueGenerator(
          field,
          new StringEqualCalculations(field.getOptions.asInstanceOf[OptionsString].getAcceptedStrings))
      case _: OptionsNumber =>
        new NumberValueGenerator(
          field,
          new NumberEqualCalculations(field.getOptions.asInstanceOf[OptionsNumber].getRanges)
        )
      case _: OptionsDate =>
        new DateValueGenerator(
          field,
          new DateEqualCalculations(
            field.getOptions.asInstanceOf[OptionsDate].getStartingDate,
            field.getOptions.asInstanceOf[OptionsDate].getTimeIncrement)
        )
    })
  }
}
