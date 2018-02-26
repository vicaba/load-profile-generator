package domain.source

import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.value.Value
import domain.value.generator.{DateValueGenerator, NumberValueGenerator, StringValueGenerator, ValueGenerator}
import domain.source.value.{SourceValueDate, SourceValueNumber, SourceValueString}

object SourceValueFactory {

  def createSourceFromGenerator(vg: ValueGenerator[_, _]): Source[Value[_], NotUsed] = vg match {
    case value@(_: StringValueGenerator) =>
      val g: Source[Value[String], NotUsed] = Source.fromGraph(new SourceValueString(value)); g
    case value@(_: NumberValueGenerator) =>
      Source.fromGraph(new SourceValueNumber(value))
    case value@(_: DateValueGenerator) =>
      Source.fromGraph(new SourceValueDate(value))
  }
}
