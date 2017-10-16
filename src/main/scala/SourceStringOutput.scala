import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import domain.value.Value

class SourceStringOutput extends GraphStage[SourceShape[Value[String]]]{
  val output = Outlet[Value[String]]("MySource.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    setHandler(output, new OutHandler {

      private var counter = 1
      override def onPull(): Unit = {
        // Here a random data will be generated based on a given input
        // For now testing without that part
        val value = new Value[String]("0001-"+counter.toString,"string","HELLO")
        push(output,value)
        counter += 1
      }
    })
  }

  override def shape: SourceShape[Value[String]] = SourceShape(output)
}
