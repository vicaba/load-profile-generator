import java.lang.System.exit

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.StatusPrinter
import domain.out.template.TemplateOutput
import domain.transform.rule.RulesCheck
import infrastructure.in.config.json.deserializer.InputConfigurationJsonReader
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReader
import org.slf4j.{Logger, LoggerFactory}

object Main {
  /** Constant used to define the path of the logback.xml, that is used to configure the Logger. */
  private final val DefaultLoggerConfigurationPath = "resources/logback.xml"

  /** The ActorSystem needed for the graph. We use the default one. */
  implicit private val system: ActorSystem = ActorSystem()
  /** The ActorMaterializer needed for the graph. We use the default one. */
  implicit private val materializer: ActorMaterializer = ActorMaterializer()

  /**
    * Method used for configuring the Logger.
    *
    * @param loggerConfigurationFilePath String with the path where we will find the configuration file.
    */
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

    configureLogger(DefaultLoggerConfigurationPath)

    val appLogger: Logger = LoggerFactory.getLogger("app.logger")

    if (args.length != 2) {
      val usageMessage = "./application <path/to/input/configuration/file> <path/to/output/configuration/file>"
      appLogger.info(usageMessage)
      println(usageMessage)
      exit(0)
    }

    appLogger.info("Starting Application")

    val inputConfigurationFile = args(0)
    val outputConfigurationFile = args(1)
    val inputConfiguration = (new InputConfigurationJsonReader).read(inputConfigurationFile)
    val outputConfiguration = (new OutputConfigurationJsonReader).read(outputConfigurationFile)
    val graphGenerator = new GraphGenerator
    val rulesCheck = new RulesCheck(inputConfiguration.getRules)
    val createTemplate: TemplateOutput = new TemplateOutput()
    createTemplate.configureTemplateSystem(outputConfiguration.getType)

    val graph = graphGenerator.generate(inputConfiguration, rulesCheck, createTemplate)
    appLogger.debug(graph.toString)
    graph.async
    graph.run()

  }
}

