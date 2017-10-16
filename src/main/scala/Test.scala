import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Graph, SourceShape}
import akka.stream.scaladsl.{Sink, Source}
import domain.in.config.ConfigHolder
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsString}
import domain.value.Value
import infrastructure.in.config.json.deserializer.InputConfigReader

import scala.concurrent.{ExecutionContextExecutor, Future}

object Test extends App {
  //val outputs = Seq(new Value[String]("0001","string","GL"))
  val inputConfig: InputConfigReader = new InputConfigReader("resources/input_date.json")
  var configGen: ConfigHolder = inputConfig.getConfigGenerator
  val inputField = configGen.getField(0)

  val sourceStringGraph = new SourceStringOutput(inputField.asInstanceOf[InputField[OptionsString]])
  val sourceIntGraph = new SourceIntegerOutput
  val sourceFloatOutput = new SourceFloatOutput
  val sourceDateOutput = new SourceDateOutput(inputField.asInstanceOf[InputField[OptionsDate]])

  //val mySourceString = Source.fromGraph(sourceStringGraph)
  //val mySourceInt = Source.fromGraph(sourceIntGraph)
  //val mySourceFloat = Source.fromGraph(sourceFloatOutput)
  val mySourceDate = Source.fromGraph(sourceDateOutput)

  //val source: Source[Value[String], NotUsed] = Source(outputs.to[scala.collection.immutable.Seq])

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  //val doneString: Future[Done] = mySourceString.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  //val doneInt: Future[Done] = mySourceInt.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  //val doneFloat: Future[Done] = mySourceFloat.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  val doneDate: Future[Done] = mySourceDate.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue.toString)))

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  //done.onComplete(_ => system.terminate())

}