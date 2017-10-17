import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.stream.{Attributes, Outlet, SourceShape}
import domain.in.field.InputField
import domain.in.field.options.Options
import domain.transform.calculations.Calculations
import domain.value.Value
import infrastructure.value.preparation.ValueGeneration

abstract class SourceValueT[I <: Options, V, T <: Calculations[V]](val inputField: InputField[I])
  extends GraphStage[SourceShape[Value[V]]] {

  val output: Outlet[Value[V]] = Outlet[Value[V]](s"Source${inputField.getName}${inputField.getId}.out")

  protected var calculations: T
  protected def dataGenerator: ValueGeneration[V, T]

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {

    setHandler(output, new OutHandler {
      override def onPull(): Unit = push(output, dataGenerator.obtainNext())

    })
  }

  override def shape: SourceShape[Value[V]] = SourceShape(output)

}

