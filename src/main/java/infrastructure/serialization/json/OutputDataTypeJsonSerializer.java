package infrastructure.serialization.json;


import com.google.gson.JsonObject;
import infrastructure.serialization.OutputDataTypeSerializer;
import output.OutputType;

interface OutputDataTypeJsonSerializer<T extends OutputType> extends OutputDataTypeSerializer<T, JsonObject> {
}

