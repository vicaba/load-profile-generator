package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import akka.stream.{ActorMaterializer, ClosedShape}
import domain.stream.stage.flow.rules.RulesFlow
import domain.value.Value

import scala.concurrent.ExecutionContextExecutor

class MergeNode(values: List[Source[Value[_], NotUsed]],
                rulesNode: RulesFlow) {
  implicit val system2: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer2: ActorMaterializer = ActorMaterializer()
  implicit val ec2: ExecutionContextExecutor = system2.dispatcher

  val listSources: List[Source[Value[_], NotUsed]] = values
  val rulesFlow: Flow[Seq[Value[_]], Seq[Value[_]], NotUsed] = Flow.fromGraph(rulesNode)

  def connectAndRunGraph(): NotUsed = {

    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val zipper = builder.add(ZipN[Value[_]](values.size))
      values.foreach(src => {
        src ~> zipper
      })


      zipper ~> rulesFlow ~> Flow[Seq[Value[_]]].map(s => s.foreach(
        src => println(src.getId + "||" + src.getType + "||" + src.getValue)
      )) ~> Sink.ignore


      ClosedShape
    }).run()

  }

}