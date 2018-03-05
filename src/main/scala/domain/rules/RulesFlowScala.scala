package domain.rules

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.transform.rule.{RulesCheck, RulesCheckScala}
import domain.value.ValueScala

import scala.collection.JavaConverters._

/**
  * The Flow responsible of applying domain.rules to a generated data that enters through its inlet,
  * and send it to the sink once domain.rules have been applied.
  *
  * @param rulesCheck The domain.rules that we need to check for all values and apply if necessary.
  */
final class RulesFlowScala(rulesCheck: RulesCheckScala) extends GraphStage[FlowShape[Seq[ValueScala[_]], Seq[ValueScala[_]]]] {
  val input: Inlet[Seq[ValueScala[_]]] = Inlet[Seq[ValueScala[_]]]("RulesFlow.in")
  val output: Outlet[Seq[ValueScala[_]]] = Outlet[Seq[ValueScala[_]]]("RulesFlow.out")
  val rules: RulesCheckScala = rulesCheck

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(input, new InHandler {

        override def onPush(): Unit = {
          val javaList = grab(input)
          val scalaList = rulesCheck.applyRules(javaList)

          push(output, scalaList)
        }
      })

      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          pull(input)
        }
      })

    }

  override def shape: FlowShape[Seq[ValueScala[_]], Seq[ValueScala[_]]] = FlowShape.of(input, output)
}
