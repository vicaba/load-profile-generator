import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import domain.in.config.ConfigHolder
import domain.in.field.InputField
import domain.in.field.options.{OptionsDate, OptionsNumber, OptionsString}
import infrastructure.in.config.json.deserializer.InputConfigReader

import scala.concurrent.{ExecutionContextExecutor, Future}

object Test extends App {
  //val outputs = Seq(new Value[String]("0001","string","GL"))
  //val inputConfig: InputConfigReader = new InputConfigReader("resources/input_string.json")
  val inputConfig: InputConfigReader = new InputConfigReader("resources/input_number.json")
  //val inputConfig: InputConfigReader = new InputConfigReader("resources/input_date.json")
  var configGen: ConfigHolder = inputConfig.getConfigGenerator
  val inputField = configGen.getField(0)

  //val sourceStringGraph = new SourceValueString(inputField.asInstanceOf[InputField[OptionsString]])
  val sourceNumberGraph = new SourceValueNumber(inputField.asInstanceOf[InputField[OptionsNumber]])
  //val sourceDateGraph = new SourceValueDate(inputField.asInstanceOf[InputField[OptionsDate]])

  //val mySourceString = Source.fromGraph(sourceStringGraph)
  val mySourceNumber = Source.fromGraph(sourceNumberGraph)
  //val mySourceDate = Source.fromGraph(sourceDateGraph)

  //val source: Source[Value[String], NotUsed] = Source(outputs.to[scala.collection.immutable.Seq])

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  //val doneString: Future[Done] = mySourceString.take(10).runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  val doneNumber: Future[Done] = mySourceNumber.take(10).runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  //val doneDate: Future[Done] = mySourceDate.take(10).runWith(Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString)))

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  //doneString.onComplete(_ => system.terminate())
  doneNumber.onComplete(_ => system.terminate())
  //doneDate.onComplete(_ => system.terminate())

}