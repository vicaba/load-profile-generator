package domain.stream.stage.sink

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler}
import akka.stream.{Attributes, Inlet, SinkShape}
import domain.out.template.TemplateOutput
import domain.value.Value

import scala.collection.JavaConverters._

/**
  * This class is the sink that we use for the graph, responsible of outputting the data using a template engine.
  *
  * @version 1.0
  * @author Albert Trias
  * @since 27/11/2017
  * @param template The template engine that we will add to this Sink.
  */
class SinkNode(val template: TemplateOutput) extends GraphStage[SinkShape[Seq[Value[_]]]] {
  /** The inlet that we will use for this sink. */
  val in: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("Sink.in")
  var counter = 0 //TODO For testing only, must be deleted later
  var firstTime = 1

  /**
    * The logic behind the Sink. It will grab the data from the sink, and pass it to the template.
    *
    * @param inheritedAttributes Not Used.
    * @return Not Used.
    */
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      /**
        * This method is necessary to start running the graph. If we don't put it, it will get stuck.
        */
      override def preStart(): Unit = pull(in)

      /**
        * The handler we put to the sink. We only use onPush for this sink.
        */
      setHandler(in, new InHandler {

        /**
          * Handler that triggers if data arrives through the inlet.
          * We will grab it and send it to the template engine.
          */
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

  /**
    * This method is needed to define the SinkShape for this sink using the inlet we defined.
    *
    * @return Returns a SinkShape that uses the inlet defined as the inlet of this Sink.
    */
  override def shape: SinkShape[Seq[Value[_]]] = SinkShape(in)
}
