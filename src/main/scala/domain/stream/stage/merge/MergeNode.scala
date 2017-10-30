package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import akka.stream.{ActorMaterializer, ClosedShape}
import domain.stream.stage.flow.rules.RulesFlow
import domain.value.Value

import scala.concurrent.ExecutionContextExecutor

class MergeNode(values: Map[String, Source[Value[_], NotUsed]],
                broadcastValues: Map[String, Broadcast[Value[_]]],
                rulesNode: RulesFlow) {
  implicit val system2: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer2: ActorMaterializer = ActorMaterializer()
  implicit val ec2: ExecutionContextExecutor = system2.dispatcher

  val listSources: Map[String, Source[Value[_], NotUsed]] = values
  val rulesFlow: Flow[Seq[Value[_]], Seq[Value[_]], NotUsed] = Flow.fromGraph(rulesNode)
  val broadcastFlows: Map[String, Broadcast[Value[_]]] = broadcastValues

  def connectAndRunGraph(): NotUsed = {

    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val zipper = builder.add(ZipN[Value[_]](values.size))

      values.keys.foreach(src =>
        if (broadcastFlows.contains(src)) {
          println("Broadcast, ID is "+src)
          val broadcast = builder.add(broadcastFlows(src))
          values(src) ~> broadcast.in
          broadcast.out(0) ~> zipper
          //TODO Connect Broadcast to Distribution, and distribution to zipper
          broadcast.out(1) ~> Sink.ignore //TO BE CHANGED
        } else {
          println("No Broadcast, ID is "+src)
          values(src) ~> zipper
        }
      )

      zipper ~> rulesFlow ~> Flow[Seq[Value[_]]].map(s => s.foreach(
        src => println(src.getId + "||" + src.getType + "||" + src.getValue)
      )) ~> Sink.ignore


      ClosedShape
    }).run()

  }

}