package stream.sink

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import akka.stream.{Attributes, Inlet, SinkShape}
import domain.out.template.TemplateOutput
import domain.value.Value

import scala.collection.JavaConverters._

/**
  * This class is the sink that we use for the graph, responsible of outputting the data using a stream.template engine.
  *
  * @version 1.0
  * @author Albert Trias
  * @since 27/11/2017
  * @param template The stream.template engine that we will add to this Sink.
  */
final class SinkNode(val template: TemplateOutput) extends GraphStage[SinkShape[Seq[Value[_]]]] {
  val in: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("Sink.in")
  var counter = 0 //TODO For testing only, must be deleted later
  var firstTime = 1

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      override def preStart(): Unit = pull(in)

      setHandler(in, new InHandler {

        override def onPush(): Unit = {
          val data = grab(in)

          template.addNewInfoToTemplateSystem(data.asJava)
          counter += 1
          /*
           * TODO createObject is for testing only. As it takes some time to generate the file, this interrupts the flow of data
           * for a noticeable time, which is not a desired behavior.
           */
          if (firstTime == 1 && counter > 10000) {
            template.generateFromTemplate()
            counter = 0
            firstTime = 0
          }
          pull(in)
        }
      })


    }

  override def shape: SinkShape[Seq[Value[_]]] = SinkShape(in)
}
