package output.data;

import com.google.gson.JsonArray;
import domain.calculations.DateEqualCalculations;
import domain.calculations.NumberEqualCalculations;
import domain.calculations.StringEqualCalculations;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import domain.options.OptionsDate;
import domain.options.OptionsNumber;
import domain.options.OptionsString;

import java.util.ArrayList;

public class JsonDataPreparation {
    private static final String INTEGER_TYPE = "integer";
    private static final String DECIMAL_TYPE = "decimal";

    private ConfigGenerator configGenerator;
    private int totalData;

    public JsonDataPreparation(ConfigGenerator configGenerator, int totalData) {

        this.configGenerator = configGenerator;
        this.totalData = totalData;
    }

    public JsonArray prepareData() {

        ArrayList<InputField> inputFields = this.configGenerator.getFields();
        JsonArray dataSet = new JsonArray();
        JsonArray data;

        for (int i = 0; i < this.totalData; i++) {

            data = new JsonArray();
            for (InputField inputField : inputFields) {

                System.out.println(
                        inputField.getId()
                                + "-"
                                + inputField.getName()
                                + "-"
                                + inputField.getType()
                                + "-"
                                + inputField.getOptions().toString());

                if (inputField.getOptions().getClass() == OptionsString.class) {

                    OptionsString optionsString = (OptionsString) inputField.getOptions();
                    StringEqualCalculations stringEqualCalculations =
                            new StringEqualCalculations(optionsString.getAcceptedStrings());

                    data.add(new StringJsonOutputSerializer().write(stringEqualCalculations.calculate()));

                } else if (inputField.getOptions().getClass() == OptionsNumber.class) {

                    OptionsNumber optionsNumber = (OptionsNumber) inputField.getOptions();
                    NumberEqualCalculations numberEqualCalculations =
                            new NumberEqualCalculations(optionsNumber.getRanges());

                    String output = "ERROR";
                    switch (optionsNumber.getType()) {
                        case INTEGER_TYPE:
                            int iResult = numberEqualCalculations.calculate().intValue();
                            output = Integer.toString(iResult);
                            break;

                        case DECIMAL_TYPE:
                            output = numberEqualCalculations.calculate().toString();
                            break;
                    }

                    data.add(new NumberJsonOutputSerializer().write(output));

                } else if (inputField.getOptions().getClass() == OptionsDate.class) {

                    OptionsDate optionsDate = (OptionsDate) inputField.getOptions();
                    DateEqualCalculations dateEqualCalculations =
                            new DateEqualCalculations(
                                    optionsDate.getStartingDate(), optionsDate.getTimeIncrement(), i);

                    data.add(new DateJsonOutputSerializer().write(dateEqualCalculations.calculate()));
                }
            }

            dataSet.add(data);
        }

        return dataSet;
    }
}
