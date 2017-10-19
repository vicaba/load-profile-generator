package domain.stream.stage.conversion

import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.stream.stage.source.{SourceValueDate, SourceValueNumber, SourceValueString}
import domain.value.Value
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}

class ConvertListGeneratorToListSource(val in: List[ValueGenerator[_,_]])
  extends ConvertListXToListY[ValueGenerator[_,_],Source[Value[_],NotUsed]]{

  override def convert(): List[Source[Value[_],NotUsed]] = {
    in.map {
      case value@(_: StringValueGenerator) =>
        Source.fromGraph(new SourceValueString(value))
      case value@(_: NumberValueGenerator) =>
        Source.fromGraph(new SourceValueNumber(value))
      case value@(_: DateValueGenerator) =>
        Source.fromGraph(new SourceValueDate(value))
    }
  }
}
