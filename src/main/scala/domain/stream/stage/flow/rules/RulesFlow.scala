package domain.stream.stage.flow.rules

import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._

/**
  * The Flow responsible of applying rules to a generated data that enters through its inlet,
  * and send it to the sink once rules have been applied.
  *
  * @param rulesCheck The rules that we need to check for all values and apply if necessary.
  */
class RulesFlow(rulesCheck: RulesCheck) extends GraphStage[FlowShape[Seq[Value[_]], Seq[Value[_]]]] {
  /** The inlet of the RulesFlow. */
  val input: Inlet[Seq[Value[_]]] = Inlet[Seq[Value[_]]]("RulesFlow.in")
  /** The outlet of the RulesFlow. */
  val output: Outlet[Seq[Value[_]]] = Outlet[Seq[Value[_]]]("RulesFlow.out")
  /** The rules that we need to check for all values and apply if necessary. */
  val rules: RulesCheck = rulesCheck

  /**
    * The logic behind the Flow. It will grab the data from the inlet, treat it, and pass it through its outlet.
    *
    * @param inheritedAttributes Not Used.
    * @return Not Used.
    */
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      /**
        * The handler we put to the flow. We use both onPush and onPull for this flow.
        */
      setHandler(input, new InHandler {
        /**
          * Handler that triggers if data arrives through the inlet.
          * We will grab it, apply rules if necessary, and send it through its outlet.
          */
        override def onPush(): Unit = {
          val javaList = grab(input).asJava
          val scalaList = rulesCheck.applyRules(javaList).asScala

          push(output, scalaList)
        }
      })

      /**
        * Handler that triggers if data is grabbed from the outlet.
        * We need to do a pull of input in that case so the flow continues,
        * else our graph will become stuck.
        */
      setHandler(output, new OutHandler {
        override def onPull(): Unit = {
          pull(input)
        }
      })

    }

  /**
    * This method is needed to define the FlowShape for this flow using the inlet and outlet we defined.
    *
    * @return Returns a FlowShape that uses the inlet and outlet defined as the inlet and outlet of this Flow.
    */
  override def shape: FlowShape[Seq[Value[_]], Seq[Value[_]]] = FlowShape.of(input, output)
}
