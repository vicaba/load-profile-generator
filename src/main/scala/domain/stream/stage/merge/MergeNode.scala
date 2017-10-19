package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import akka.stream.{ActorMaterializer, ClosedShape}
import domain.value.Value

import scala.concurrent.ExecutionContextExecutor

class MergeNode {
  implicit val system2: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer2: ActorMaterializer = ActorMaterializer()
  implicit val ec2: ExecutionContextExecutor = system2.dispatcher

  def connectSourcesWithMerge(values: List[Source[Value[_], NotUsed]]): Unit =

    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val zipper = builder.add(ZipN[Value[_]](values.size))
      values.foreach(src => {
        src ~> zipper
      })
      zipper ~> Flow[Seq[Value[_]]].map(s => s.foreach(
        src => println(src.getId + "||" + src.getType + "||" + src.getValue)
      )) ~> Sink.ignore

      /*val merge = builder.add(Merge[Value[_]](values.size))
      values.foreach(src => src ~> merge)
      merge ~> Flow[Value[_]].map { s => println(s.getId+"-"+s.getType+"-"+s.getValue.toString); s } ~> Sink.ignore*/
      ClosedShape
    }).run()

}