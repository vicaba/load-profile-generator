package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;
import infrastructure.value.serialization.ValueSerializer;

public interface JsonValueSerializer<T extends String> extends ValueSerializer<T, JsonObject> {}
