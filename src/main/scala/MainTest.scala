import domain.graph.GraphGenerator
import domain.out.template.{FreemakerTemplateSystemScala, TemplateOutputScala}
import domain.transform.rule.RulesCheckScala
import infrastructure.in.config.json.deserializer.InputConfigurationJsonReaderScala
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReaderScala

object MainTest {
  def main(args: Array[String]): Unit = {
    val inputConfigurationFile = "resources/input.json"
    val inputConfiguration = new InputConfigurationJsonReaderScala(inputConfigurationFile).readJson4()
    val outputConfigurationFile = "resources/output.json"
    val outputField = new OutputConfigurationJsonReaderScala(outputConfigurationFile).readJson4()
    val graphGenerator = new GraphGenerator
    val rulesCheck = new RulesCheckScala(inputConfiguration.getRules)

    val templateSystemScala = new FreemakerTemplateSystemScala(outputField.getNameTemplate, outputField.getOutputType)
    val createTemplate = new TemplateOutputScala(templateSystemScala)
    createTemplate.configureTemplateSystem()
  }
}
