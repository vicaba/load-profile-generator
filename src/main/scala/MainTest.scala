import infrastructure.in.config.json.deserializer.InputConfigurationJsonReaderScala
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReaderScala

object MainTest {
  def main(args: Array[String]): Unit = {
    val inputConfigurationFile = "resources/input.json"
    new InputConfigurationJsonReaderScala(inputConfigurationFile).readJson4()
    val outputConfigurationFile = "resources/output.json"
    new OutputConfigurationJsonReaderScala(outputConfigurationFile).readJson4()
  }
}
