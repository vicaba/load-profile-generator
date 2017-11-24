package domain.stream.stage.sink

import akka.stream.{Attributes, Inlet, SinkShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import domain.value.Value

class SinkNode extends GraphStage[SinkShape[Seq[Value[_]]]]{
  val in: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("Sink.in")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {
      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          val data = grab(in)
          
        }
      })
    }

  override def shape: SinkShape[Seq[Value[_]]] = SinkShape(in)
}
