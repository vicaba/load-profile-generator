package domain.stream.stage.connector

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source, ZipN}
import domain.in.distribution.InputDistribution
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.flow.template.TemplateSerializerFlow
import domain.stream.stage.sink.SinkNode
import domain.value.Value

/**
  * Class responsible for connect all nodes together.
  *
  * @param sourceValues       A Map with all sources, using their own ids as key.
  * @param broadcastValues    A Map with all broadcasts, using the ids of the connected sources as key.
  * @param distributionValues A Map with all distribution flows, using their own ids as key.
  * @param listConnections    A Map with all the broadcasts that connect to the DistributionFlow,
  *                           using the DistributionFlow id as key.
  * @param rulesNode          The Rules Flow that will apply rules to the generated data.
  * @param templateFlow       The TemplateSerializerFlow that will grab a data and change it to String, the content of the String is defined by the template selected.
  */
class GraphConnector(sourceValues: Map[String, Source[Value[_], NotUsed]],
                     broadcastValues: Map[String, Broadcast[Value[_]]],
                     distributionValues: Map[String, Flow[Value[_], Value[_], NotUsed]],
                     listConnections: Map[String, List[InputDistribution]],
                     rulesNode: RulesFlow,
                     templateFlow: TemplateSerializerFlow) {

  /**
    * Method used to connect all nodes together.
    *
    * @return Returns a complete Runnable Graph.
    */
  def connect(): RunnableGraph[NotUsed] = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._

    /*
     * We create a zipper responsible of receiving all the values received from each node
     * that have a value generator and put it together inside a Seq
     */
    val zipper = builder.add(ZipN[Value[_]](sourceValues.size + distributionValues.size))

    /* Here we connect all sources that don't connect with a broadcast with this zipper. */
    sourceValues foreach (src =>
      if (!broadcastValues.contains(src._1)) {
        src._2 ~> zipper
      }
      )

    /*
     * Here we build the Broadcast nodes using the map we receive.
     * This step is necessary so we can connect these nodes.
     */
    val broadBuild = broadcastValues
      .map(broad => broad._1 -> builder.add(broad._2))

    /* Once we're done building them, we first connect all sources that have to connect to a broadcast,
     * and then use the first outlet to connect it to the final zipper. */
    for ((id, broad) <- broadBuild) {
      sourceValues(id) ~> broad.in
      broad.out(0) ~> zipper
    }

    /* Here we connect all the broadcasts. It has two behaviors. */
    distributionValues foreach { flow =>
      val conn = listConnections(flow._1)

      if (conn.size == 1) {
        /*
         * If we only need to connect one broadcast to a distribution flow, we do it all in this step,
         * connect those two mentioned then connecting the flow to the final zipper.
         */
        broadBuild(conn.head.getId).out(1) ~> flow._2 ~> zipper
      } else {
        /*
         * If there's more than one, we need to build a zipper to connector all broadcasts together,
         * and connect all broadcasts to it.
         */
        val zipperMerge = builder.add(ZipN[Value[_]](conn.size))
        conn.foreach { conn =>
          broadBuild(conn.getId).out(1) ~> zipperMerge
        }

        /* Once we have the broadcasts connected, we connect the zipper to the distribution flow,
         * and lastly the flow to the final zipper.
         */
        zipperMerge ~> Flow[Seq[Value[_]]].map(src => src.toList.head) ~> flow._2 ~> zipper
      }
    }

    /*
     * In this last step we connect the zipper to the RulesFlow,
     * and the flow to the sink, completing the graph after closing it.
     */
    zipper ~> rulesNode ~> templateFlow ~> Sink.ignore //sinkNode
    ClosedShape
  })

}