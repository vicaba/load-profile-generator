package domain.stream.stage.merge

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source, ZipN}
import akka.stream.{ActorMaterializer, ClosedShape}
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

      val zipper = builder.add(ZipN[Value[_]](sourceValues.size + distributionValues.size))


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
        val strCount = "NOPE"+counted
        sourceValues(id) ~> Flow[Value[_]].map {src => println(strCount+":"+src.getId + "||" + src.getType + "||" + src.getValue); src}  ~> broad.in
        println("---Connecting " + broad.out(0).toString() + " with zipper")
        broad.out(0) ~> zipper
        println("---Connected")
        counted = counted + 1
      }

      println("3. Time to connect Broadcast with Distribution")
      var counted2 = 0
      distributionValues foreach { flow =>
        val conn = listConnections(flow._1)
        if (conn.size == 1) {
          println("---Connecting " + broadBuild(conn.head.getId).out(1) + " with " + flow._2)
          broadBuild(conn.head.getId).out(1) ~> flow._2 ~> zipper
          println("---Connected")
        } else {
          println("---There's multiple distributions")
          val merge = builder.add(Merge[Value[_]](conn.size))
          conn.foreach { conn =>
            println("---Connecting " + broadBuild(conn.getId).out(1) + " with " + merge)
            val strCounted2 = "LOL"+counted2
            broadBuild(conn.getId).out(1) ~> Flow[Value[_]].map {src => println(strCounted2+": "+src.getId + "||" + src.getType + "||" + src.getValue); src} ~> merge
            println("---Connected")
            counted2 += 1
          }
          println("---Connecting " + merge + " with " + flow._2)
          merge ~> flow._2 ~> zipper
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