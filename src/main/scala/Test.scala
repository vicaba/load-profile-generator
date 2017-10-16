import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Graph, SourceShape}
import akka.stream.scaladsl.{Sink, Source}
import domain.value.Value

import scala.concurrent.{ExecutionContextExecutor, Future}

object Test extends App {
  val outputs = Seq(new Value[String]("0001","string","GL"))
  val sourceGraph = new SourceStringOutput
  val mySource = Source.fromGraph(sourceGraph)
  //val source: Source[Value[String], NotUsed] = Source(outputs.to[scala.collection.immutable.Seq])
  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  //val result1: Future[Done] = mySource.take(1).runForeach(i => println(i.getId+" "+i.getType+" "+i.getValue))(materializer)
  //val done: Future[Done] = mySource.runForeach(i => println(i.getId+" "+i.getType+" "+i.getValue))(materializer)
  val done: Future[Done] = mySource.runWith(Sink.foreach(i => println(i.getId+" "+i.getType+" "+i.getValue)))

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  //done.onComplete(_ => system.terminate())

}