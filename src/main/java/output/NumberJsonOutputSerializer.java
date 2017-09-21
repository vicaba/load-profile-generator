package output;

import com.google.gson.JsonObject;
import config.OptionsNumber;

class NumberJsonOutputSerializer implements JsonOutputSerializer<OptionsNumber> {
    private OutputCalculations calculations;

    private static final String TYPE_FIELD = "type";
    private static final String VALUE_FIELD = "value";
    private static final String TYPE_VALUE = "number";

    NumberJsonOutputSerializer(OutputCalculations calculations) {
        this.calculations = calculations;
    }

    @Override
    public JsonObject write(OptionsNumber optionsNumber) {
        //TODO In the future the E value will change to a value inside the field that decides the probability method. Most likely the function itself will also change.
        int whichRange = calculations.calculateRangeInteger(
                0,
                optionsNumber.getTotalRanges()-1,
                'E'
        );

        //TODO Lacking compatibility with decimals. Will add a switch to decide which one in the future.
        String output = Integer.toString(calculations.calculateRangeInteger(
                optionsNumber.getRangeMin(whichRange),
                optionsNumber.getRangeMax(whichRange),
                'E'
        ));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TYPE_FIELD,TYPE_VALUE);
        jsonObject.addProperty(VALUE_FIELD,output);
        return jsonObject;
    }

}
