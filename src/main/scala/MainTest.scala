import domain.graph.GraphGenerator
import domain.transform.rule.RulesCheckScala
import infrastructure.in.config.json.deserializer.InputConfigurationJsonReaderScala
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReaderScala

object MainTest {
  def main(args: Array[String]): Unit = {
    val inputConfigurationFile = "resources/input.json"
    val inputConfiguration = new InputConfigurationJsonReaderScala(inputConfigurationFile).readJson4()
    val outputConfigurationFile = "resources/output.json"
    new OutputConfigurationJsonReaderScala(outputConfigurationFile).readJson4()
    val graphGenerator = new GraphGenerator
    val rulesCheck = new RulesCheckScala(inputConfiguration.getRules)
  }
}
