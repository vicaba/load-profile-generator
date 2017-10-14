package infrastructure.in.serialization.json.deserializer;

import com.google.gson.*;
import domain.in.field.InputField;
import domain.in.field.InputFieldInfo;
import domain.in.field.options.Options;
import domain.in.field.options.OptionsDate;
import domain.in.field.options.OptionsNumber;
import domain.in.field.options.OptionsString;

import java.lang.reflect.Type;

public class FieldDeserializer implements JsonDeserializer<InputField> {

  private static final String OPTIONS_FIELD = "options";
  private static final String INFO_FIELD = "info";

  // ----- FOR ANY NEW OPTION TYPE, ADD ITS NAME HERE ----
  private static final String STRING_TYPE = "string";
  private static final String NUMBER_TYPE = "number";
  private static final String DATE_TYPE = "date";
  // ----- END OPTIONS TYPE ----

  @Override
  public InputField deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    if (json == null) return null;

    JsonObject jo = json.getAsJsonObject();

    return getDeserializeField(jo);
  }

  private InputField getDeserializeField(JsonObject jo) {

    InputFieldInfo inputFieldInfo =
        new Gson().fromJson(jo.get(INFO_FIELD).getAsJsonObject(), InputFieldInfo.class);
    Options options = getOptionsFromJSON(jo, inputFieldInfo.getType());

    return new InputField<>(inputFieldInfo, options);
  }

  private Options getOptionsFromJSON(JsonObject jo, String sType) {

    switch (sType) {
      case STRING_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsString.class);
      case NUMBER_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsNumber.class);
      case DATE_TYPE:
        return new Gson().fromJson(jo.get(OPTIONS_FIELD).getAsJsonObject(), OptionsDate.class);
      default:
        return null;
    }
  }
}
