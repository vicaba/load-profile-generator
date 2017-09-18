import config.Config;
import config.ConfigGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Config config = new Config();
        ConfigGenerator configGen;
        if ((configGen = config.configureFromJSON()) != null) {

        }
    }
}
