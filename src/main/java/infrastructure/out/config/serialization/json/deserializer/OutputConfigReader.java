package infrastructure.out.config.serialization.json.deserializer;

import com.google.gson.Gson;
import domain.out.field.OutputField;
import infrastructure.helper.BufferedFileReader;

import java.io.BufferedReader;

public class OutputConfigReader {
  private OutputField outputField;

  public OutputConfigReader(String jsonPath) {
    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader br = bufferedReaderTreatment.getReader();

    this.outputField = new Gson().fromJson(br, OutputField.class);

    bufferedReaderTreatment.closeReader();
  }

  public OutputField getOutputField() {
    return this.outputField;
  }
}
