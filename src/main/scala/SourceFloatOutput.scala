import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import domain.value.Value

class SourceFloatOutput extends GraphStage[SourceShape[Value[Double]]] {
  val output: Outlet[Value[Double]] = Outlet[Value[Double]]("MySourceFloat.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    setHandler(output, new OutHandler {

      private var counter = 1
      override def onPull(): Unit = {
        // Here a random data will be generated based on a given input
        // For now testing without that part
        // NOTE: In scala we need double to use the float value in java
        val value = new Value[Double]("0001-"+counter.toString,"decimal",54.3)
        push(output,value)
        counter += 1
      }
    })
  }

  override def shape: SourceShape[Value[Double]] = SourceShape(output)
}
