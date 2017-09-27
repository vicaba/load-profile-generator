package output.config;

import com.google.gson.Gson;
import domain.output.OutputField;

import java.io.BufferedReader;

public class OutputConfig {
    private static final String JSON_PATH = "resources/output.json";

    private OutputField outputField;

    public OutputConfig() {
        BufferedReaderTreatment bufferedReaderTreatment = new BufferedReaderTreatment(JSON_PATH);
        BufferedReader br = bufferedReaderTreatment.getReader();

        this.outputField = new Gson().fromJson(br, OutputField.class);

        bufferedReaderTreatment.closeReader();
    }

    public OutputField getOutputField() {
        return this.outputField;
    }
}
