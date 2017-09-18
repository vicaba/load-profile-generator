package config;

import com.google.gson.*;

import java.lang.reflect.Type;

public class FieldDeserializer implements JsonDeserializer<Field> {
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String TYPE_FIELD = "type";
    private final static String OPTIONS_FIELD = "options";

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
        //---- Configure this part to adapt to your own field, if necessary ----
        int iID = jo.get(ID_FIELD).getAsInt();              //Field ID
        String sName = jo.get(NAME_FIELD).getAsString();    //Field Name
        String sType = jo.get(TYPE_FIELD).getAsString();    //Field Type
        Options options = getOptionsFromJSON(jo, sType);    //Field Options

        //---- Don't forget to modify the constructor ----
        return new Field(iID, sName, sType, options);
    }

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