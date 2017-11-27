package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import domain.in.distribution.InputDistribution
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.sink.SinkNode
import domain.value.Value
import org.slf4j.{Logger, LoggerFactory}

class MergeNode(sourceValues: Map[String, Source[Value[_], NotUsed]],
                broadcastValues: Map[String, Broadcast[Value[_]]],
                distributionValues: Map[String, Flow[Value[_], Value[_], NotUsed]],
                listConnections: Map[String, List[InputDistribution]],
                rulesNode: RulesFlow,
                sinkNode: SinkNode) {
  //implicit val config: Config = ConfigFactory.load()

  //implicit val logger: Logger = LoggerFactory.getLogger("GraphLogger")

  def connect(): RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._

    val zipper = builder.add(ZipN[Value[_]](sourceValues.size + distributionValues.size)
      /*
      .async
      .addAttributes(Attributes.inputBuffer(initial = 1024, max = 1024))  //Only works with power of two.
      */
    )

    sourceValues foreach (src =>
      if (!broadcastValues.contains(src._1)) {
        src._2 ~> zipper
      }
      )

    val broadBuild = broadcastValues
      .map(broad => broad._1 -> builder.add(broad._2))

    for ((id, broad) <- broadBuild) {
      sourceValues(id) ~> broad.in
      broad.out(0) ~> zipper
    }

    distributionValues foreach { flow =>
      val conn = listConnections(flow._1)
      //flow._2.async
      //flow._2.buffer(10,OverflowStrategy.dropHead)
      if (conn.size == 1) {
        broadBuild(conn.head.getId).out(1) ~> flow._2 ~> zipper
      } else {
        val zipperMerge = builder.add(ZipN[Value[_]](conn.size))
        //val zipperMerge = builder.add(Merge[Value[_]](conn.size))
        conn.foreach { conn =>
          broadBuild(conn.getId).out(1) ~> zipperMerge
        }

        zipperMerge ~> Flow[Seq[Value[_]]].map(src => src.toList.head) ~> flow._2 ~> zipper
        //zipperMerge ~> flow._2 ~> zipper
      }
    }

    /*
    zipper ~> rulesNode ~> Flow[Seq[Value[_]]].map(s => s.foreach(
      src => println(src.getId + "||" + src.getType + "||" + src.getValue)
    )) ~> Sink.ignore
    */

    zipper ~> rulesNode ~> sinkNode  //Sink.ignore //sinkNode

    ClosedShape
  })

}