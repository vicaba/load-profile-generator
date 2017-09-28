package output.data;

import com.google.gson.JsonObject;

public class DateJsonOutputSerializer implements JsonOutputSerializer<String> {
  private static final String TYPE_FIELD = "type";
  private static final String VALUE_FIELD = "value";
  private static final String TYPE_VALUE = "date";

  @Override
  public JsonObject write(String output) {

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty(TYPE_FIELD, TYPE_VALUE);
    jsonObject.addProperty(VALUE_FIELD, output);

    return jsonObject;
  }
}
