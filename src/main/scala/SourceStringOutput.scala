import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import domain.in.field.InputField
import domain.in.field.options.OptionsString
import domain.value.Value
import infrastructure.value.preparation.DataStringPreparation

class SourceStringOutput(inputField: InputField[OptionsString]) extends GraphStage[SourceShape[Value[String]]]{
  val output: Outlet[Value[String]] = Outlet[Value[String]]("MySourceString.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    setHandler(output, new OutHandler {

      override def onPull(): Unit = {
        // Here a random data will be generated based on a given input
        // For now testing without that part
        val dataPreparation = new DataStringPreparation(inputField)

        push(output,dataPreparation.obtainPreparedValue())
      }
    })
  }

  override def shape: SourceShape[Value[String]] = SourceShape(output)
}
