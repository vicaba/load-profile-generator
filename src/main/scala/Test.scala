import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import domain.value.Value

import scala.concurrent.{ExecutionContextExecutor, Future}

object Test extends App {
  val outputs = Seq(new Value[String]("0001","string","GL"))
  val source: Source[Value[String], NotUsed] = Source(outputs.to[scala.collection.immutable.Seq])
  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val done: Future[Done] = source.runForeach(i => println(i.getId+" "+i.getType+" "+i.getValue))(materializer)

  implicit val ec: ExecutionContextExecutor = system.dispatcher
  done.onComplete(_ => system.terminate())

}