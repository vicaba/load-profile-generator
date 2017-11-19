package infrastructure.out.config.serialization.json.deserializer;

import com.google.gson.Gson;
import domain.out.field.OutputField;
import infrastructure.helper.BufferedFileReader;

import java.io.BufferedReader;

public class OutputConfigurationJsonReader {


  public OutputConfigurationJsonReader() {
  }

  public OutputField read(String jsonPath) {
    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader br = bufferedReaderTreatment.getReader();

    final OutputField outputField =  new Gson().fromJson(br, OutputField.class);

    bufferedReaderTreatment.closeReader();

    return outputField;
  }

}
