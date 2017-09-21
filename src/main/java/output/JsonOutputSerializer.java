package output;

import com.google.gson.JsonObject;
import config.Options;

interface JsonOutputSerializer<T extends Options> extends OutputSerializer<T, JsonObject> {

}
