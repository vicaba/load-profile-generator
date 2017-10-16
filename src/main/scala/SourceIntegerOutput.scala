import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import domain.value.Value

class SourceIntegerOutput extends GraphStage[SourceShape[Value[Int]]] {
  val output: Outlet[Value[Int]] = Outlet[Value[Int]]("MySourceInt.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    setHandler(output, new OutHandler {

      private var counter = 1
      override def onPull(): Unit = {
        // Here a random data will be generated based on a given input
        // For now testing without that part
        val value = new Value[Int]("0001-"+counter.toString,"integer",54)
        push(output,value)
        counter += 1
      }
    })
  }

  override def shape: SourceShape[Value[Int]] = SourceShape(output)
}
