package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;
import domain.value.Value;

import java.time.LocalDateTime;

public class DateJsonValueSerializer extends JsonValueSerializer<Value<LocalDateTime>> {

  @Override
  protected String typeValue() {
    return "date";
  }

  @Override
  protected String valueToStringTransformer(Value<LocalDateTime> value) {
    return value.getValue().toString();
  }

}
