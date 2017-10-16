package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;
import domain.value.Value;

public class NumberJsonValueSerializer extends JsonValueSerializer<Value<Number>> {

  @Override
  protected String typeValue() {
    return "number";
  }

  @Override
  protected String valueToStringTransformer(Value<Number> value) {
    return value.getValue().toString();
  }
}
