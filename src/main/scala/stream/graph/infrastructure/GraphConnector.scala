package stream.graph.infrastructure

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import domain.in.distribution.InputDistribution
import stream.rules.infrastructure.RulesFlow
import stream.template.infrastructure.TemplateSerializerFlow
import domain.value.Value

/**
  * Class responsible for connect all nodes together.
  *
  * @param sourceValues       A Map with all sources, using their own ids as key.
  * @param broadcastValues    A Map with all broadcasts, using the ids of the connected sources as key.
  * @param distributionValues A Map with all stream.distribution flows, using their own ids as key.
  * @param listConnections    A Map with all the broadcasts that connect to the DistributionFlow,
  *                           using the DistributionFlow id as key.
  * @param rulesNode          The Rules Flow that will apply stream.rules to the generated data.
  * @param templateFlow       The TemplateSerializerFlow that will grab a data and change it to String, the content of the String is defined by the stream.template selected.
  */
final class GraphConnector(sourceValues: Map[String, Source[Value[_], NotUsed]],
                     broadcastValues: Map[String, Broadcast[Value[_]]],
                     distributionValues: Map[String, Flow[Value[_], Value[_], NotUsed]],
                     listConnections: Map[String, List[InputDistribution]],
                     rulesNode: RulesFlow,
                     templateFlow: TemplateSerializerFlow) {


  def connect(): RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._

    val zipper = builder.add(ZipN[Value[_]](sourceValues.size + distributionValues.size))

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

      if (conn.lengthCompare(1) == 0) {
        broadBuild(conn.head.getId).out(1) ~> flow._2 ~> zipper

      } else {
        val zipperMerge = builder.add(ZipN[Value[_]](conn.size))
        conn.foreach { conn =>
          broadBuild(conn.getId).out(1) ~> zipperMerge
        }

        zipperMerge ~> Flow[Seq[Value[_]]].map(src => src.toList.head) ~> flow._2 ~> zipper
      }
    }

    zipper ~> rulesNode ~> templateFlow ~> Sink.ignore //sinkNode
    ClosedShape
  })

}