package config;

import com.google.gson.*;
import domain.field.Field;
import domain.field.FieldInfo;
import domain.options.Options;
import domain.options.OptionsDate;
import domain.options.OptionsNumber;
import domain.options.OptionsString;

import java.lang.reflect.Type;

public class FieldDeserializer implements JsonDeserializer<Field> {
    /*private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String TYPE_FIELD = "type";
    */
    private final static String OPTIONS_FIELD = "options";
    private final static String INFO_FIELD = "info";

    //----- FOR ANY NEW OPTION TYPE, ADD ITS NAME HERE ----
    private final static String STRING_TYPE = "string";
    private final static String NUMBER_TYPE = "number";
    private final static String DATE_TYPE = "date";
    //----- END OPTIONS TYPE ----

    @Override
    public Field deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null) return null;

        JsonObject jo = json.getAsJsonObject();

        return getDeserializeField(jo);
    }

    private Field getDeserializeField(JsonObject jo) {
        FieldInfo fieldInfo = new Gson().fromJson(
                jo.get(INFO_FIELD).getAsJsonObject(),FieldInfo.class);
        Options options = getOptionsFromJSON(jo, fieldInfo.getType());

        return new Field<>(fieldInfo, options);
    }

    //TODO Need to ask if this is fine or not according to the SOLID principle, O.
    private Options getOptionsFromJSON(JsonObject jo, String sType) {
        switch (sType) {
            case STRING_TYPE:
                return new Gson().fromJson(
                        jo.get(OPTIONS_FIELD).getAsJsonObject(),OptionsString.class);
            case NUMBER_TYPE:
                return new Gson().fromJson(
                        jo.get(OPTIONS_FIELD).getAsJsonObject(),OptionsNumber.class);
            case DATE_TYPE:
                return new Gson().fromJson(
                        jo.get(OPTIONS_FIELD).getAsJsonObject(),OptionsDate.class);
            default:
                return null;
        }
    }
}