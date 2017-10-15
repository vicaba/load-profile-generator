package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;
import domain.value.Value;

public class StringJsonValueSerializer extends JsonValueSerializer<Value<String>> {

  @Override
  protected String typeValue() {
    return "string";
  }

  @Override
  protected String valueToStringTransformer(Value<String> value) {
    return value.getValue();
  }
}
