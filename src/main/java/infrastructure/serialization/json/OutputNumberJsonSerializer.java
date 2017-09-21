package infrastructure.serialization.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import output.OutputTypeNumber;

public class OutputNumberJsonSerializer implements OutputDataTypeJsonSerializer<OutputTypeNumber> {

  @Override
  public JsonObject write(OutputTypeNumber o) {
    JsonObject obj = new JsonObject();
    obj.add("value", new JsonPrimitive(o.getData()));
    return obj;
  }
}
