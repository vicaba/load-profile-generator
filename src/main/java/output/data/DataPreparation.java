package output.data;

import domain.calculations.DateEqualCalculations;
import domain.calculations.NumberEqualCalculations;
import domain.calculations.StringEqualCalculations;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import domain.options.OptionsDate;
import domain.options.OptionsNumber;
import domain.options.OptionsString;
import domain.output.Output;

import java.util.ArrayList;

public class DataPreparation {
  private static final String INTEGER_TYPE = "integer";
  private static final String DECIMAL_TYPE = "decimal";

  private ConfigGenerator configGenerator;
  // private int totalData;

  public DataPreparation(ConfigGenerator configGenerator) {
    this.configGenerator = configGenerator;
  }

  public ArrayList<Output> prepareData() {
    ArrayList<InputField> inputFields = this.configGenerator.getFields();
    ArrayList<Output> data = new ArrayList<>();

    for (InputField inputField : inputFields) {
      /*
      System.out.println(
          inputField.getId()
              + "-"
              + inputField.getName()
              + "-"
              + inputField.getType()
              + "-"
              + inputField.getOptions().toString());
      */
      if (inputField.getOptions().getClass() == OptionsString.class) {

        OptionsString optionsString = (OptionsString) inputField.getOptions();
        StringEqualCalculations stringEqualCalculations =
            new StringEqualCalculations(optionsString.getAcceptedStrings());
        Output<String> output = new Output<>("string", stringEqualCalculations.calculate());
        data.add(output);

      } else if (inputField.getOptions().getClass() == OptionsNumber.class) {

        OptionsNumber optionsNumber = (OptionsNumber) inputField.getOptions();
        NumberEqualCalculations numberEqualCalculations =
            new NumberEqualCalculations(optionsNumber.getRanges());

        switch (optionsNumber.getType()) {
          case INTEGER_TYPE:
            int iResult = numberEqualCalculations.calculate().intValue();
            Output<Integer> outputInt = new Output<>("number", iResult);
            data.add(outputInt);
            break;

          case DECIMAL_TYPE:
            float fResult = numberEqualCalculations.calculate();
            Output<Float> outputFloat = new Output<>("number", fResult);
            data.add(outputFloat);
            break;
        }

      } else if (inputField.getOptions().getClass() == OptionsDate.class) {

        OptionsDate optionsDate = (OptionsDate) inputField.getOptions();
        DateEqualCalculations dateEqualCalculations =
            new DateEqualCalculations(
                optionsDate.getStartingDate(),
                optionsDate.getTimeIncrement(),
                optionsDate.getMaxDates());

        Output<String> output = new Output<>("date", dateEqualCalculations.calculate());
        data.add(output);
      }
    }

    return data;
  }
}
