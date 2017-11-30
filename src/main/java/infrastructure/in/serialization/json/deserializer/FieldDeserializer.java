package infrastructure.in.serialization.json.deserializer;

import com.google.gson.*;
import domain.in.field.DistributionInfo;
import domain.in.field.InputField;
import domain.in.field.InputFieldInfo;
import domain.in.field.options.Options;
import domain.in.field.options.OptionsDate;
import domain.in.field.options.OptionsNumber;
import domain.in.field.options.OptionsString;

import java.lang.reflect.Type;

/**
 * Class used to deserialize the json, as there's one property in it that depending on its value we
 * need to treat it in different ways.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class FieldDeserializer implements JsonDeserializer<InputField> {

  private static final String OPTIONS_FIELD = "options";
  private static final String INFO_FIELD = "info";
  private static final String DISTRIBUTION_INFO = "distributionInfo";

  // ----- FOR ANY NEW OPTION TYPE, ADD ITS NAME HERE ----
  private static final String STRING_TYPE = "string";
  private static final String INTEGER_TYPE = "integer";
  private static final String DECIMAL_TYPE = "decimal";
  private static final String DATE_TYPE = "date";
  // ----- END OPTIONS TYPE ----

  /**
   * Method used to deserialize the json, implemented from the JsonDeserializer interface. The main
   * part is inside the private function below.
   *
   * @param json The json element we want to deserialize
   * @param typeOfT Not Used in this case.
   * @param context Not Used in this case.
   * @return Returns an InputField, the content on which depends on the json property that forces us
   *     to deserialize.
   * @throws JsonParseException Throws exception in case there's an error in the json file.
   */
  @Override
  public InputField deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    if (json == null) return null;

    JsonObject jo = json.getAsJsonObject();

    return getDeserializeField(jo);
  }

  /**
   * Method used to deserialize the field. Checks the value of the field, and depending on it, the
   * Options inside the InputField will be one that is compatible with that value.
   *
   * @param jo The JsonObject to deserialize.
   * @return Returns an InputField with an Options that matches the value of the InputField.
   */
  private InputField getDeserializeField(JsonObject jo) {

    InputFieldInfo inputFieldInfo =
        new Gson().fromJson(jo.get(INFO_FIELD).getAsJsonObject(), InputFieldInfo.class);
    Options options = getOptionsFromJSON(jo, inputFieldInfo.getType());
    DistributionInfo distributionInfo =
        new Gson().fromJson(jo.get(DISTRIBUTION_INFO).getAsJsonObject(), DistributionInfo.class);

    return new InputField<>(inputFieldInfo, options, distributionInfo);
  }

  /**
   * Method used to create the correct Options according to the type of value the InputField will
   * have.
   *
   * @param jo The JsonObject that we want to deserialize.
   * @param sType The type of value we're dealing with.
   * @return Returns an Options object that corresponds with the type of value.
   */
  private Options getOptionsFromJSON(JsonObject jo, String sType) {

    switch (sType) {
      case STRING_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsString.class);
      case INTEGER_TYPE:
      case DECIMAL_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsNumber.class);
      case DATE_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsDate.class);
      default:
        return null;
    }
  }
}
