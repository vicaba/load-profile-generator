package domain.graph

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import domain.in.distribution.InputDistributionScala
import domain.rules.RulesFlowScala
import domain.template.TemplateSerializerFlowScala
import domain.value.ValueScala

/**
  * Class responsible for connect all nodes together.
  *
  * @param sourceValues       A Map with all sources, using their own ids as key.
  * @param broadcastValues    A Map with all broadcasts, using the ids of the connected sources as key.
  * @param distributionValues A Map with all stream.distribution flows, using their own ids as key.
  * @param listConnections    A Map with all the broadcasts that connect to the DistributionFlow,
  *                           using the DistributionFlow id as key.
  * @param rulesNode          The Rules Flow that will apply domain.rules to the generated data.
  * @param templateFlow       The TemplateSerializerFlow that will grab a data and change it to String, the content of the String is defined by the domain.template selected.
  */
final class GraphConnectorScala(sourceValues: Map[String, Source[ValueScala[_], NotUsed]],
                                broadcastValues: Map[String, Broadcast[ValueScala[_]]],
                                distributionValues: Map[String, Flow[ValueScala[_], ValueScala[_], NotUsed]],
                                listConnections: Map[String, Seq[InputDistributionScala]],
                                rulesNode: RulesFlowScala,
                                templateFlow: TemplateSerializerFlowScala) {


  def connect(): RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._

    val zipper = builder.add(ZipN[ValueScala[_]](sourceValues.size + distributionValues.size))

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
        broadBuild(conn.head.id).out(1) ~> flow._2 ~> zipper

      } else {
        val zipperMerge = builder.add(ZipN[ValueScala[_]](conn.size))
        conn.foreach { conn =>
          broadBuild(conn.id).out(1) ~> zipperMerge
        }

        zipperMerge ~> Flow[Seq[ValueScala[_]]].map(src => src.toList.head) ~> flow._2 ~> zipper
      }
    }

    zipper ~> rulesNode ~> templateFlow ~> Sink.ignore //sinkNode
    ClosedShape
  })

}