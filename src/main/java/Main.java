import config.Config;
import config.ConfigGenerator;
import output.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------Hello World!----------");
        Config config = new Config();
        ConfigGenerator configGen;
        if ((configGen = config.configureFromJSON()) != null) {
            OutputConfig outputConfig = new OutputConfig();
            if (outputConfig.configureFromJson()) {
                OutputCalculations outputCalculations = new OutputCalculations();
                JsonFileOutput jsonFileOutput = new JsonFileOutput();
                //TODO To change so it passes OutputConfig
                jsonFileOutput.writeToFile(jsonFileOutput.prepareData(configGen, outputConfig.getAmount(), outputCalculations));
            }

        }
        System.out.println("------------Bye World!----------");
    }
}
