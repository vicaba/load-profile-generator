package infrastructure.in.config.json.deserializer;

import com.google.gson.GsonBuilder;
import domain.in.config.InputConfiguration;
import domain.in.field.InputField;
import infrastructure.helper.BufferedFileReader;
import infrastructure.in.serialization.json.deserializer.FieldDeserializer;

import java.io.BufferedReader;

public class InputConfigurationJsonReader {

  public InputConfigurationJsonReader() {

  }

  public InputConfiguration read(String jsonPath) {

    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader bufferedReader = bufferedReaderTreatment.getReader();

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(InputField.class, new FieldDeserializer());
    final InputConfiguration inputConfiguration = gsonBuilder.create().fromJson(bufferedReader, InputConfiguration.class);

    bufferedReaderTreatment.closeReader();

    return inputConfiguration;
  }

}
