import domain.config.ConfigGenerator;
import domain.output.Output;
import domain.output.OutputField;
import output.config.InputConfig;
import output.config.OutputConfig;
import output.data.DataPreparation;
import output.rule.RulesApplication;
import output.stream.test.StreamExample;

import java.util.ArrayList;

public class Main {
  private static final String FILE_METHOD = "file";
  private static final String STREAM_METHOD = "stream";
  private static final String JSON_TYPE = "json";

  public static void main(String[] args) {
    System.out.println("------------Hello World!----------");
    if (args.length != 2) {
      System.out.println(
          "Hi there, don't forget that you need to add the paths first"
              + "towards your input file and second to your output file, in this order.");
    } else {
      InputConfig inputConfig = new InputConfig(args[0]);
      ConfigGenerator configGen;

      if ((configGen = inputConfig.getConfigGenerator()) != null) {
        OutputField outputField = new OutputConfig(args[1]).getOutputField();
        DataPreparation dataPreparation = new DataPreparation(configGen);
        ArrayList<ArrayList<Output>> outputs = new ArrayList<>();
        RulesApplication rulesApplication = new RulesApplication(configGen.getRules());

        for (int i = 0; i < outputField.getAmount(); i++) {
          ArrayList<Output> auxOutput = dataPreparation.prepareData();
          rulesApplication.applyRules(auxOutput);
          outputs.add(auxOutput);
        }

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
    }
    System.out.println("------------Bye World!----------");
  }
}
