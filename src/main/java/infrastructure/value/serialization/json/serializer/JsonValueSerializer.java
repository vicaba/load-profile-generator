package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;
import infrastructure.value.serialization.ValueSerializer;

public abstract class JsonValueSerializer<T> implements ValueSerializer<T, JsonObject> {

  protected final String TYPE_FIELD = "type";
  protected final String VALUE_FIELD = "value";

  @Override
  public JsonObject write(T value) {

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty(TYPE_FIELD, typeValue());
    jsonObject.addProperty(VALUE_FIELD, valueToStringTransformer(value));

    return jsonObject;
  }

  protected abstract String typeValue();

  protected abstract String valueToStringTransformer(T value);
}
