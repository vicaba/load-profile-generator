package output;

import com.google.gson.JsonObject;
import config.OptionsString;

public class StringJsonOutputSerializer implements JsonOutputSerializer<OptionsString> {
    private OutputCalculations calculations;

    private static final String TYPE_FIELD = "type";
    private static final String VALUE_FIELD = "value";
    private static final String TYPE_VALUE = "string";

    StringJsonOutputSerializer(OutputCalculations calculations) {
        this.calculations = calculations;
    }

    @Override
    public JsonObject write(OptionsString options) {
        //TODO In the future the E value will change to a value inside the field that decides the probability method. Most likely the function itself will also change.
        String output =  options.getAcceptedString(
                calculations.calculateRangeInteger(
                        0,
                        options.getSizeAccepted()-1,
                        'E')
        );

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TYPE_FIELD,TYPE_VALUE);
        jsonObject.addProperty(VALUE_FIELD,output);
        return jsonObject;

    }

}
