package domain.rules

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._

/**
  * The Flow responsible of applying domain.rules to a generated data that enters through its inlet,
  * and send it to the sink once domain.rules have been applied.
  *
  * @param rulesCheck The domain.rules that we need to check for all values and apply if necessary.
  */
final class RulesFlow(rulesCheck: RulesCheck) extends GraphStage[FlowShape[Seq[Value[_]], Seq[Value[_]]]] {
  val input: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("RulesFlow.in")
  val output: Outlet[Seq[Value[_]]] = Outlet[Seq[Value[_]]]("RulesFlow.out")
  val rules: RulesCheck = rulesCheck

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(input, new InHandler {

        override def onPush(): Unit = {
          val javaList = grab(input).asJava
          val scalaList = rulesCheck.applyRules(javaList).asScala

          push(output, scalaList)
        }
      })

      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          pull(input)
        }
      })

    }

  override def shape: FlowShape[Seq[Value[_]], Seq[Value[_]]] = FlowShape.of(input, output)
}
