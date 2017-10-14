package infrastructure.in.config.json.deserializer;

import com.google.gson.GsonBuilder;
import domain.in.config.ConfigHolder;
import domain.in.field.InputField;
import infrastructure.helper.BufferedFileReader;
import infrastructure.in.serialization.json.deserializer.FieldDeserializer;

import java.io.BufferedReader;

public class InputConfigReader {

  private ConfigHolder configGenerator;

  public InputConfigReader(String jsonPath) {
    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader bufferedReader = bufferedReaderTreatment.getReader();

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(InputField.class, new FieldDeserializer());
    this.configGenerator = gsonBuilder.create().fromJson(bufferedReader, ConfigHolder.class);

    bufferedReaderTreatment.closeReader();

    System.out.println(gsonBuilder.create().toJson(this.configGenerator));
  }

  public ConfigHolder getConfigGenerator() {

    return this.configGenerator;
  }
}
