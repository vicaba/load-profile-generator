import domain.config.ConfigGenerator;
import domain.output.OutputField;
import output.config.InputConfig;
import output.config.OutputConfig;
import output.data.JsonDataPreparation;
import output.file.JsonFileOutput;
import output.stream.test.StreamExample;

public class Main {
  private static final String FILE_METHOD = "file";
  private static final String STREAM_METHOD = "stream";
  private static final String JSON_TYPE = "json";

  public static void main(String[] args) {
    System.out.println("------------Hello World!----------");
    InputConfig inputConfig = new InputConfig();
    ConfigGenerator configGen;
    if ((configGen = inputConfig.getConfigGenerator()) != null) {

      OutputField outputField = new OutputConfig().getOutputField();
      switch (outputField.getMethod()) {
        case FILE_METHOD:
          switch (outputField.getType()) {
            case JSON_TYPE:
              JsonDataPreparation jsonDataPreparation =
                  new JsonDataPreparation(configGen, outputField.getAmount());

              new JsonFileOutput().writeToFile(jsonDataPreparation.prepareData());
              break;
            default:
              break;
          }

          break;
        case STREAM_METHOD:
          new StreamExample().example();
          break;
        default:
          break;
      }
    }
    System.out.println("------------Bye World!----------");
  }
}
