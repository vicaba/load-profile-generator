package infrastructure.serialization.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import infrastructure.serialization.DummyType;
import output.OutputTypeNumber;

public class OutputNumberJsonSerializer implements OutputDataTypeJsonSerializer<DummyType> {

  @Override
  public JsonObject write(DummyType o) {
    JsonObject obj = new JsonObject();
    obj.add("value", new JsonPrimitive(o.data));
    return obj;
  }
}
