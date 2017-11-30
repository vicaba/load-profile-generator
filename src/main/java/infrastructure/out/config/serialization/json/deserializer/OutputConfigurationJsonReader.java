package infrastructure.out.config.serialization.json.deserializer;

import com.google.gson.Gson;
import domain.out.field.OutputField;
import infrastructure.helper.BufferedFileReader;

import java.io.BufferedReader;

/**
 * Class responsible of reading the configuration file that defines how the data is outputted with
 * Gson.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class OutputConfigurationJsonReader {

  /**
   * Method we use to create the OutputField object by reading the config file and treating the json
   * inside with Gson.
   *
   * @param jsonPath The pathname of the config file.
   * @return Returns a new OutputField object.
   */
  public OutputField read(String jsonPath) {
    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader br = bufferedReaderTreatment.getReader();

    final OutputField outputField = new Gson().fromJson(br, OutputField.class);

    bufferedReaderTreatment.closeReader();

    return outputField;
  }
}
