package domain.stream.stage.flow.rules

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.transform.rule.RulesCheck
import domain.value.Value
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._


class RulesFlow(rulesCheck: RulesCheck) extends GraphStage[FlowShape[Seq[Value[_]], Seq[Value[_]]]] {

  val input: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("RulesFlow.in")
  val output: Outlet[Seq[Value[_]]] = Outlet[Seq[Value[_]]]("RulesFlow.out")

  val rules: RulesCheck = rulesCheck
  val logger: Logger = LoggerFactory.getLogger("GraphLogger")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      setHandler(input, new InHandler {
        override def onPush(): Unit = {
          val javaList = grab(input).asJava
          val scalaList = rulesCheck.applyRules(javaList).asScala
          logger.debug("Result of rules from list values: {}", scalaList.map(value => value.getId +":"+value.getType+":"+value.getValue))

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
