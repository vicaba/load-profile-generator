package domain.source

import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.source.value.{SourceValueDateScala, SourceValueNumberScala, SourceValueStringScala}
import domain.value.ValueScala
import domain.value.generator._

object SourceValueFactoryScala {

  def createSourceFromGenerator(vg: ValueGeneratorScala[_, _]): Source[ValueScala[_], NotUsed] = vg match {
    case value@(_: StringValueGeneratorScala) =>
      Source.fromGraph(new SourceValueStringScala(value))
    case value@(_: NumberValueGeneratorScala) =>
      Source.fromGraph(new SourceValueNumberScala(value))
    case value@(_: DateValueGeneratorScala) =>
      Source.fromGraph(new SourceValueDateScala(value))
  }
}
