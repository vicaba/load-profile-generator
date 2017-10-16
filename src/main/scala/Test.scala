import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Graph, SourceShape}
import akka.stream.scaladsl.{Sink, Source}
import domain.value.Value

import scala.concurrent.{ExecutionContextExecutor, Future}

object Test extends App {
  //val outputs = Seq(new Value[String]("0001","string","GL"))
  val sourceStringGraph = new SourceStringOutput
  val sourceIntGraph = new SourceIntegerOutput
  val sourceFloatOutput = new SourceFloatOutput
  val sourceDateOutput = new SourceDateOutput

  //val mySource = Source.fromGraph(sourceStringGraph)
  //val mySource = Source.fromGraph(sourceIntGraph)
  //val mySource = Source.fromGraph(sourceFloatOutput)
  val mySource = Source.fromGraph(sourceDateOutput)

  //val source: Source[Value[String], NotUsed] = Source(outputs.to[scala.collection.immutable.Seq])

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  //val doneString: Future[Done] = mySource.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  //val doneInt: Future[Done] = mySource.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  //val doneFloat: Future[Done] = mySource.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))
  val doneDate: Future[Done] = mySource.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue.toString)))

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  //done.onComplete(_ => system.terminate())

}