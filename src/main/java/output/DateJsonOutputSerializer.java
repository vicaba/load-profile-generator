package output;

import com.google.gson.JsonObject;
import config.OptionsDate;

public class DateJsonOutputSerializer implements JsonOutputSerializer<OptionsDate> {
    private OutputCalculations calculations;
    private int cycle;

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final String TYPE_FIELD = "type";
    private static final String VALUE_FIELD = "value";
    private static final String TYPE_VALUE = "date";

    public DateJsonOutputSerializer(OutputCalculations calculations, int cycle) {
        this.calculations = calculations;
        this.cycle = cycle;
    }

    @Override
    public JsonObject write(OptionsDate optionsDate) {
        String output =  calculations.calculateNextDate(
                optionsDate.getStartingDate(),
                optionsDate.getTimeIncrement(),
                this.cycle,
                DATE_FORMAT
        );

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TYPE_FIELD,TYPE_VALUE);
        jsonObject.addProperty(VALUE_FIELD,output);
        return jsonObject;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
