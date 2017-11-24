package domain.stream.stage.sink

import akka.stream.{Attributes, Inlet, SinkShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import domain.value.Value
import example.template.CreateTemplate

import scala.collection.JavaConverters._

class SinkNode(val template: CreateTemplate) extends GraphStage[SinkShape[Seq[Value[_]]]]{
  val in: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("Sink.in")
  //var counter = 0   //For testing only, must be deleted later

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      override def preStart(): Unit = pull(in)

      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          val data = grab(in)

          template.addNewInfo(data.asJava)
          //counter += 1
          /*
           * createObject is for testing only. As it takes some time to generate the file, this interrupts the flow of data
           * for a noticeable time, which is not a desired behavior.
           */
          //if (counter > 100) template.createObjectTemplate()
          pull(in)
        }
      })


    }

  override def shape: SinkShape[Seq[Value[_]]] = SinkShape(in)
}
