package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source, ZipN}
import akka.stream._
import domain.in.distribution.InputDistribution
import domain.stream.stage.flow.rules.RulesFlow
import domain.value.Value

import scala.concurrent.ExecutionContextExecutor

class MergeNode(sourceValues: Map[String, Source[Value[_], NotUsed]],
                broadcastValues: Map[String, Broadcast[Value[_]]],
                distributionValues: Map[String, Flow[Value[_], Value[_], NotUsed]],
                listConnections: Map[String, List[InputDistribution]],
                rulesNode: RulesFlow) {
  implicit val system2: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer2: ActorMaterializer = ActorMaterializer()
  implicit val ec2: ExecutionContextExecutor = system2.dispatcher

  def connectAndRunGraph(): NotUsed = {
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val zipper = builder.add(ZipN[Value[_]](sourceValues.size + distributionValues.size)
        .async
        .addAttributes(Attributes.inputBuffer(initial = 1024, max = 1024))  //Only works with power of two.
      )
      println("1. Time to connect sources to zipper")
      sourceValues foreach (src =>
        if (!broadcastValues.contains(src._1)) {
          println("---Connecting Source " + src._1.toString + " with zipper")
          src._2 ~> zipper
          println("---Connected")
        }
        )

      val broadBuild = broadcastValues
        .map(broad => broad._1 -> builder.add(broad._2))


      println("2. Time to connect sources with broadcast. Broad size is " + broadcastValues.size)
      var counted = 0
      for ((id, broad) <- broadBuild) {
        println("---Connecting " + sourceValues(id).toString() + " with " + broad.in.toString())
        sourceValues(id) ~> broad.in
        println("---Connecting " + broad.out(0).toString() + " with zipper")
        broad.out(0) ~> zipper
        println("---Connected")
        counted = counted + 1
      }

      println("3. Time to connect Broadcast with Distribution")
      var counted2 = 0
      distributionValues foreach { flow =>
        val conn = listConnections(flow._1)
        flow._2.async
        flow._2.buffer(10,OverflowStrategy.dropHead)
        if (conn.size == 1) {
          println("---Connecting " + broadBuild(conn.head.getId).out(1) + " with " + flow._2)
          broadBuild(conn.head.getId).out(1) ~> flow._2 ~> zipper
          println("---Connected")
        } else {
          println("---There's multiple distributions")
          //val zipperMerge = builder.add(ZipN[Value[_]](conn.size))
          val zipperMerge = builder.add(Merge[Value[_]](conn.size))
          conn.foreach { conn =>
            println("---Connecting " + broadBuild(conn.getId).out(1) + " with " + zipperMerge)
            broadBuild(conn.getId).out(1) ~> zipperMerge
            println("---Connected")
            counted2 += 1
          }

          println("---Connecting " + zipperMerge + " with " + flow._2)
          //zipperMerge ~> Flow[Seq[Value[_]]].map(src => src.toList.head) ~>  flow._2 ~> zipper
          zipperMerge ~> flow._2 ~> zipper
          println("---Connected")
        }
      }

      zipper ~> rulesNode ~> Flow[Seq[Value[_]]].map(s => s.foreach(
        src => println(src.getId + "||" + src.getType + "||" + src.getValue)
      )) ~> Sink.ignore

      ClosedShape
    }).run

  }

}