import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import domain.in.config.ConfigHolder
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import domain.stream.stage.source.{SourceValueDate, SourceValueNumber, SourceValueString, SourceValueT}
import domain.transform.calculations.{DateEqualCalculations, NumberEqualCalculations, StringEqualCalculations}
import domain.value.Value
import infrastructure.in.config.json.deserializer.InputConfigReader
import infrastructure.value.preparation.{DateValueGenerator, NumberValueGenerator, StringValueGenerator}

import scala.languageFeature.implicitConversions
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Success

object Test extends App {

  implicit def sourceValueTToSource[V](st: SourceValueT[V, _]): Source[Value[V], NotUsed] = Source.fromGraph(st)

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val inputConfig: InputConfigReader = new InputConfigReader("resources/input_allcombined.json")

  var configGen: ConfigHolder = inputConfig.getConfigGenerator

  val inputFieldNumber =
    configGen.getField(1).asInstanceOf[InputField[OptionsNumber]]
  val dataGeneratorNumber = new NumberValueGenerator(
    inputFieldNumber,
    new NumberEqualCalculations(inputFieldNumber.getOptions.getRanges)
  )
  val sourceGraphNumber = new SourceValueNumber(dataGeneratorNumber)
  val doneNumber: Future[Done] = sourceGraphNumber
    .take(10)
    .runWith(Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString)))

  val inputFieldString =
    configGen.getField(0).asInstanceOf[InputField[OptionsString]]
  val dataGeneratorString = new StringValueGenerator(
    inputFieldString,
    new StringEqualCalculations(inputFieldString.getOptions.getAcceptedStrings)
  )
  val sourceGraphString = new SourceValueString(dataGeneratorString)
  val doneString: Future[Done] = sourceGraphString
    .take(10)
    .runWith(Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString)))

  val inputFieldDate =
    configGen.getField(2).asInstanceOf[InputField[OptionsDate]]
  val dataGeneratorDate = new DateValueGenerator(
    inputFieldDate,
    new DateEqualCalculations(
      inputFieldDate.getOptions.getStartingDate,
      inputFieldDate.getOptions.getTimeIncrement
    )
  )
  val sourceGraphDate = new SourceValueDate(dataGeneratorDate)
  val doneDate: Future[Done] = sourceGraphDate
    .take(10)
    .runWith(Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString)))

  Future.sequence(Seq(doneNumber, doneString, doneDate)).andThen {
    case _ => system.terminate()
  }

}
