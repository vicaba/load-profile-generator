import config.Config;
import config.ConfigGenerator;
import output.OutputText;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------Hello World!----------");
        Random randomSeed = new Random();
        Config config = new Config();
        ConfigGenerator configGen;
        if ((configGen = config.configureFromJSON()) != null) {
            OutputText outputText = new OutputText(configGen, randomSeed);
            outputText.generateFileOutput(20);
        }
        System.out.println("------------Bye World!----------");
    }
}
