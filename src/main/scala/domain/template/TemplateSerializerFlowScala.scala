package domain.template

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.out.template.{TemplateOutput, TemplateOutputScala}
import domain.value.ValueScala

final class TemplateSerializerFlowScala(template: TemplateOutputScala) extends GraphStage[FlowShape[Seq[ValueScala[_]],String]]{
  val inlet: Inlet[Seq[ValueScala[_]]] = Inlet("InValues.in")
  val outlet: Outlet[String] = Outlet("OutStringValue.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {
      setHandler(inlet, new InHandler {
        override def onPush(): Unit = {
          val data = grab(inlet)
          val result = template.obtainTemplateString(data)
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

  override def shape: FlowShape[Seq[ValueScala[_]], String] = FlowShape(inlet,outlet)
}
