package infrastructure.value.serialization.json.serializer;

import com.google.gson.JsonObject;

public interface JsonOutputSerializer<T extends String> extends OutputSerializer<T, JsonObject> {}
