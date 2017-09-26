package output;

import com.google.gson.JsonObject;
import config.OptionsNumber;

public class NumberJsonOutputSerializer implements JsonOutputSerializer<OptionsNumber> {
    private OutputCalculations calculations;

    private static final String TYPE_FIELD = "type";
    private static final String VALUE_FIELD = "value";
    private static final String TYPE_VALUE = "number";

    public NumberJsonOutputSerializer(OutputCalculations calculations) {
        this.calculations = calculations;
    }

    @Override
    public JsonObject write(OptionsNumber optionsNumber) {
        //TODO To change in the future for better compatibility with different probability methods. Should take into account the SOLID principle.
        int whichRange = calculations.calculateRangeInteger(
                0,
                optionsNumber.getTotalRanges()-1,
                'E'
        );

        //TODO Lacking compatibility with decimals. Consider SOLID principle here, specially O.
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
