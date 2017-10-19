import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import domain.in.config.ConfigHolder
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.stream.stage.merge.MergeNode
import domain.stream.stage.source.{SourceValueDate, SourceValueNumber, SourceValueString}
import domain.transform.calculations.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import infrastructure.in.config.json.deserializer.InputConfigReader
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator}

import scala.concurrent.ExecutionContextExecutor
import scala.languageFeature.implicitConversions

object Test extends App {
  //implicit def sourceValueTToSource[V](st: SourceValueT[V, _]): Source[Value[V], NotUsed] = Source.fromGraph(st)

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val inputConfig: InputConfigReader = new InputConfigReader("resources/input_allcombined.json")

  var configGen: ConfigHolder = inputConfig.getConfigGenerator

  val inputFieldNumber =
    configGen.getField(1).asInstanceOf[InputField[OptionsNumber]]

  val inputFieldString =
    configGen.getField(0).asInstanceOf[InputField[OptionsString]]

  val inputFieldDate =
    configGen.getField(2).asInstanceOf[InputField[OptionsDate]]

  val values = List(inputFieldString, inputFieldNumber, inputFieldDate)
    .map(field => field.getOptions match {
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
    .map {
      case value@(_: StringValueGenerator) =>
        Source.fromGraph(new SourceValueString(value))
      case value@(_: NumberValueGenerator) =>
        Source.fromGraph(new SourceValueNumber(value))
      case value@(_: DateValueGenerator) =>
        Source.fromGraph(new SourceValueDate(value))
    }

  val mergeRun: Unit = new MergeNode().connectSourcesWithMerge(values)

  //TODO The combine method merges all sources into one.
  //TODO If for example we have 3 columns, it will generate an entire data each 3 iterations (test below generates 10 data of 3 columns).
  //TODO The plan would be to grab the 3 columns, put them together in one single List, and pass them to a Sink
  //TODO This sink will do whatever job we need it to do with the merged value.
  //TODO Now to figure out how to do it with any number of columns...
  /*  val mergedResult: Future[Done] = merged.take(30).runWith(
      Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString))
    )*/

  /*
  Future.sequence(Seq(doneNumber, doneString, doneDate)).andThen {
    case _ => system.terminate()
  }
  */
}
