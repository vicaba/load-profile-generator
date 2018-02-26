package stream.template.infrastructure

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.out.template.TemplateOutput
import domain.value.Value

import scala.collection.JavaConverters._

class TemplateSerializerFlow(template: TemplateOutput) extends GraphStage[FlowShape[Seq[Value[_]],String]]{
  val inlet: Inlet[Seq[Value[_]]] = Inlet("InValues.in")
  val outlet: Outlet[String] = Outlet("OutStringValue.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {
      setHandler(inlet, new InHandler {
        override def onPush(): Unit = {
          val data = grab(inlet)
          val result = template.obtainTemplateString(data.asJava)
          println("-----Result is: "+result)
          push(outlet, result)
        }
      })

      setHandler(outlet, new OutHandler {
        override def onPull(): Unit = {
          pull(inlet)
        }
      })
    }

  override def shape: FlowShape[Seq[Value[_]], String] = FlowShape(inlet,outlet)
}
