import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import output.config.InputConfig;
import domain.config.ConfigGenerator;
import domain.calculations.OutputCalculations;
import domain.field.InputField;
import domain.options.OptionsString;
import domain.output.OutputField;
import output.file.JsonFileOutput;
import output.config.OutputConfig;
import output.StringJsonSerializer;
import output.data.JsonDataPreparation;

import java.util.concurrent.CompletionStage;

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
          example();
          break;
        default:
          break;
      }
    }
    System.out.println("------------Bye World!----------");
  }

  private static void example() {
    final Source<Integer, NotUsed> source = Source.repeat(1);
    final ActorSystem system = ActorSystem.create("QuickStart");
    final Materializer materializer = ActorMaterializer.create(system);
    OutputCalculations outputCalculations = new OutputCalculations();
    StringJsonSerializer stringJsonSerializer = new StringJsonSerializer();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    InputConfig inputConfig = new InputConfig();
    ConfigGenerator configGen;

    if ((configGen = inputConfig.getConfigGenerator()) != null) {
      InputField inputField = configGen.getField(0);
      Source<JsonObject, NotUsed> source2 =
              source
                      .map(i -> ((OptionsString) inputField.getOptions()))
                      .map(j -> j.getAcceptedString(outputCalculations.calculateRangeInteger(0, 2, 'E')))
                      .map(stringJsonSerializer::write);
      final CompletionStage<Done> done =
              source2.runForeach(i -> System.out.println(gson.toJson(i)), materializer);
      done.thenRun(system::terminate);
    }
  }
}
