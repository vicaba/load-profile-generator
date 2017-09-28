package output.config;

import com.google.gson.Gson;
import domain.output.OutputField;

import java.io.BufferedReader;

public class OutputConfig {
  private OutputField outputField;

  public OutputConfig(String jsonPath) {
    BufferedReaderTreatment bufferedReaderTreatment = new BufferedReaderTreatment(jsonPath);
    BufferedReader br = bufferedReaderTreatment.getReader();

    this.outputField = new Gson().fromJson(br, OutputField.class);

    bufferedReaderTreatment.closeReader();
  }

  public OutputField getOutputField() {
    return this.outputField;
  }
}
