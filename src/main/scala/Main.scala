import java.lang.System.exit
import java.util

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.StatusPrinter
import domain.transform.rule.RulesCheck
import domain.value.Value
import example.stream.test.StreamExample
import example.template.CreateTemplate
import infrastructure.helper.BufferedFileReader
import infrastructure.in.config.json.deserializer.InputConfigurationJsonReader
import infrastructure.out.config.serialization.json.deserializer.OutputConfigurationJsonReader
import infrastructure.value.preparation.DataPreparation
import org.slf4j.{Logger, LoggerFactory}

object Main {

  implicit private val system = ActorSystem()
  implicit private val materializer = ActorMaterializer()

  private val DefaultLoggerConfigurationPath = "resources/logback.xml"
  private val FileMethod = "file"
  private val StreamMethod = "stream"
  private val JsonType = "json"

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
    val createTemplate: CreateTemplate = new CreateTemplate(outputConfiguration.getType)

    val graph = graphGenerator.generate(inputConfiguration, rulesCheck, createTemplate)
    appLogger.debug(graph.toString)
    graph.async
    graph.run()



    // TODO: Do you still need DataPreparation?
    /*
    val outputField = (new OutputConfigurationJsonReader).read(outputConfigurationFile)
    val dataPreparation = new DataPreparation(inputConfiguration)
    val outputs = new util.ArrayList[util.ArrayList[Value[_]]]
    var i = 0
    while ( {
      i < outputField.getAmount
    }) {
      val auxOutput: util.ArrayList[Value[_]] = dataPreparation.prepareData(i)
      rulesCheck.applyRules(auxOutput)
      outputs.add(auxOutput)

      {
        i += 1
        i - 1
      }
    }
    */


    /*
    outputField.getMethod match {
      case FileMethod =>
        outputField.getType match {
          case JsonType =>
          // new JsonFileOutput().writeToFile(jsonArray);
          case _ =>
        }
      case StreamMethod =>
        // new StreamExample().example1();
        new StreamExample().example6(inputConfiguration)
      case _ =>
    }
    */
  }
}

