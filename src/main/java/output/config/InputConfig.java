package output.config;

import com.google.gson.GsonBuilder;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import output.field.FieldDeserializer;

import java.io.BufferedReader;

public class InputConfig {
  private ConfigGenerator configGenerator;

  public InputConfig(String jsonPath) {
    BufferedReaderTreatment bufferedReaderTreatment = new BufferedReaderTreatment(jsonPath);
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
