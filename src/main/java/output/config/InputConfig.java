package output.config;

import com.google.gson.GsonBuilder;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import output.field.FieldDeserializer;

import java.io.BufferedReader;

public class InputConfig {
    //TODO Consider obtaining path of the config file through the main arguments.
    private static final String JSON_PATH = "resources/config.json";

    private ConfigGenerator configGenerator;

    public InputConfig() {
        BufferedReaderTreatment bufferedReaderTreatment = new BufferedReaderTreatment(JSON_PATH);
        BufferedReader bufferedReader = bufferedReaderTreatment.getReader();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(InputField.class, new FieldDeserializer());
        this.configGenerator = gsonBuilder.create().fromJson(bufferedReader, ConfigGenerator.class);

        bufferedReaderTreatment.closeReader();

        System.out.println(gsonBuilder.create().toJson(this.configGenerator));
    }

    public ConfigGenerator getConfigGenerator() {

        return this.configGenerator;
    }
}
