package infrastructure.in.config.json.deserializer;

import com.google.gson.GsonBuilder;
import domain.in.config.InputConfiguration;
import domain.in.field.InputField;
import infrastructure.helper.BufferedFileReader;
import infrastructure.in.serialization.json.deserializer.FieldDeserializer;

import java.io.BufferedReader;

/**
 * Class we used to read the configuration file that defines all InputFields with Gson.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class InputConfigurationJsonReader {

  /**
   * Method we use to create the InputConfiguration object by reading the config file and treating
   * the json inside with Gson.
   *
   * @param jsonPath The pathname of the config file.
   * @return Returns a new InputConfiguration object.
   */
  public InputConfiguration read(String jsonPath) {

    BufferedFileReader bufferedReaderTreatment = new BufferedFileReader(jsonPath);
    BufferedReader bufferedReader = bufferedReaderTreatment.getReader();

    GsonBuilder gsonBuilder = new GsonBuilder();
    /*
     * Since there's information that varies depending on the value of one property,
     * we use a FieldDeserializer to be able to treat all the different cases.
     */
    gsonBuilder.registerTypeAdapter(InputField.class, new FieldDeserializer());
    final InputConfiguration inputConfiguration =
        gsonBuilder.create().fromJson(bufferedReader, InputConfiguration.class);

    bufferedReaderTreatment.closeReader();

    return inputConfiguration;
  }
}
