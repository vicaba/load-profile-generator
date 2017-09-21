import config.Config;
import config.ConfigGenerator;
import output.FileOutput;
import output.JsonFileOutput;
import output.Output;
import output.OutputCalculations;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------Hello World!----------");
        Config config = new Config();
        ConfigGenerator configGen;
        if ((configGen = config.configureFromJSON()) != null) {
            //TODO Read a config file that decides the type of output, if file or stream, and if json or others, plus the amount of data
            OutputCalculations outputCalculations = new OutputCalculations();
            JsonFileOutput jsonFileOutput = new JsonFileOutput();
            jsonFileOutput.writeToFile(jsonFileOutput.prepareData(configGen,20,outputCalculations));

        }
        System.out.println("------------Bye World!----------");
    }
}
