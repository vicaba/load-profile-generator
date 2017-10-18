package domain.stream.stage.merge

import java.time.LocalDateTime

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import domain.value.Value

import scala.concurrent.ExecutionContextExecutor

class MergeNode {
  implicit val system2: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer2: ActorMaterializer = ActorMaterializer()
  implicit val ec2: ExecutionContextExecutor = system2.dispatcher

  def connectSourcesWithMerge(values: List[Source[Value[_ >: String with java.lang.Float with LocalDateTime], NotUsed]]): Unit =

    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val merge = builder.add(Merge[Value[_ >: String with java.lang.Float with LocalDateTime]](values.size))
      values.foreach(src => src ~> merge)
      merge ~> Flow[Value[_ >: String with java.lang.Float with LocalDateTime]].map { s => println(s.getId+"-"+s.getType+"-"+s.getValue.toString); s } ~> Sink.ignore
      ClosedShape
    }).run()

}