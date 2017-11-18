import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import domain.in.config.ConfigHolder;
import domain.out.field.OutputField;
import domain.transform.rule.RulesCheck;
import domain.value.Value;
import example.stream.test.StreamExample;
import example.template.CreateTemplate;
import infrastructure.in.config.json.deserializer.InputConfigReader;
import infrastructure.out.config.serialization.json.deserializer.OutputConfigReader;
import infrastructure.value.preparation.DataPreparation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static java.lang.System.exit;

public class Main {

  private static final String LOGGER_CONFIGURATION_PATH = "resources/logback.xml";

  private static final String FILE_METHOD = "file";
  private static final String STREAM_METHOD = "stream";
  private static final String JSON_TYPE = "json";

  private static void configureLogger(String loggerConfigurationFilePath) {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    try {
      JoranConfigurator configurator = new JoranConfigurator();
      configurator.setContext(context);
      // Call context.reset() to clear any previous configuration, e.g. default
      // configuration. For multi-step configuration, omit calling context.reset().
      context.reset();
      configurator.doConfigure(loggerConfigurationFilePath);
    } catch (JoranException je) {
      je.printStackTrace();
    }
    StatusPrinter.printInCaseOfErrorsOrWarnings(context);
  }

  public static void main(String[] args) {

    configureLogger(LOGGER_CONFIGURATION_PATH);

    Logger logger = LoggerFactory.getLogger("AppLogger");

    logger.info("Starting Application");
    if (args.length != 2) {
      final String usage =
          "./application <path/to/input/configuration/file> <path/to/output/configuration/file>";
      System.out.println(usage);
      exit(0);
    }

    final String inputConfigurationFile = args[0];
    final String outputConfigurationFile = args[1];

    InputConfigReader inputConfig = new InputConfigReader(inputConfigurationFile);
    ConfigHolder configGen;

    if ((configGen = inputConfig.getConfigGenerator()) != null) {
      GraphGenerator graphGenerator = new GraphGenerator();
      RulesCheck rulesCheck = new RulesCheck(configGen.getRules());
      /*
      graphGenerator.generate(
          configGen.getFields(), rulesCheck, configGen.getDistributions()
      );
      */
      graphGenerator.generate(configGen, rulesCheck);
      OutputField outputField = new OutputConfigReader(outputConfigurationFile).getOutputField();
      DataPreparation dataPreparation = new DataPreparation(configGen);
      ArrayList<ArrayList<Value>> outputs = new ArrayList<>();

      for (int i = 0; i < outputField.getAmount(); i++) {
        ArrayList<Value> auxOutput = dataPreparation.prepareData(i);
        rulesCheck.applyRules(auxOutput);
        outputs.add(auxOutput);
      }

      CreateTemplate createTemplate = new CreateTemplate();
      createTemplate.createObjectTemplate(outputs);

      switch (outputField.getMethod()) {
        case FILE_METHOD:
          switch (outputField.getType()) {
            case JSON_TYPE:
              // new JsonFileOutput().writeToFile(jsonArray);
              break;
            default:
              break;
          }

          break;
        case STREAM_METHOD:
          // new StreamExample().example1();
          new StreamExample().example6(configGen);
          break;
        default:
          break;
      }
    }

    System.out.println("------------Bye World!----------");
  }
}
