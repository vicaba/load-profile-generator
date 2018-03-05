import java.lang.System.exit

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.StatusPrinter
import domain.graph.GraphGeneratorScala
import domain.out.template.{FreemakerTemplateSystemScala, TemplateOutputScala}
import domain.transform.rule.RulesCheckScala
import infrastructure.in.config.json.deserializer.InputConfigurationJsonReaderScala
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReaderScala
import org.slf4j.{Logger, LoggerFactory}

object MainTest {
  private final val DefaultLoggerConfigurationPath = "resources/logback.xml"

  implicit private val system: ActorSystem = ActorSystem()
  implicit private val materializer: ActorMaterializer = ActorMaterializer()

  private def configureLogger(loggerConfigurationFilePath: String): Unit = {
    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    try {
      val configurator: JoranConfigurator = new JoranConfigurator
      configurator.setContext(context)
      // Call context.reset() to clear any previous configuration, e.g. default
      // configuration. For multi-step configuration, omit calling context.reset().
      context.reset()
      configurator.doConfigure(loggerConfigurationFilePath)
    } catch {
      case je: JoranException =>
        je.printStackTrace()
    }
    StatusPrinter.printInCaseOfErrorsOrWarnings(context)
  }

  def main(args: Array[String]): Unit = {

    /*
    configureLogger(DefaultLoggerConfigurationPath)

    val appLogger: Logger = LoggerFactory.getLogger("app.logger")

    if (args.length != 2) {
      val usageMessage = "./application <path/to/input/configuration/file> <path/to/output/configuration/file>"
      appLogger.info(usageMessage)
      println(usageMessage)
      exit(0)
    }

    appLogger.info("Starting Application")
    */

    val inputConfigurationFile = "resources/input.json"
    val inputConfiguration = new InputConfigurationJsonReaderScala(inputConfigurationFile).readJson4()

    val outputConfigurationFile = "resources/output.json"
    val outputField = new OutputConfigurationJsonReaderScala(outputConfigurationFile).readJson4()

    val rulesCheck = new RulesCheckScala(inputConfiguration.getRules)

    val templateSystemScala = new FreemakerTemplateSystemScala(outputField.getNameTemplate, outputField.getOutputType)
    val createTemplate = new TemplateOutputScala(templateSystemScala)
    createTemplate.configureTemplateSystem()

    val graphGenerator = new GraphGeneratorScala
    val graph = graphGenerator.generate(inputConfiguration, rulesCheck, createTemplate)
    //appLogger.debug(graph.toString)
    //graph.async
    //graph.run()
  }
}
