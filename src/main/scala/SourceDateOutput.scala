import java.time.LocalDateTime

import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import domain.value.Value

class SourceDateOutput extends GraphStage[SourceShape[Value[LocalDateTime]]]{
  val output: Outlet[Value[LocalDateTime]] = Outlet[Value[LocalDateTime]]("MySourceDate.out")

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    setHandler(output, new OutHandler {

      private var counter = 1
      override def onPull(): Unit = {
        // Here a random data will be generated based on a given input
        // For now testing without that part
        // NOTE: In scala we need double to use the float value in java
        val value = new Value[LocalDateTime]("0001-"+counter.toString,"decimal",LocalDateTime.now())
        push(output,value)
        counter += 1
      }
    })
  }

  override def shape: SourceShape[Value[LocalDateTime]] = SourceShape(output)
}
