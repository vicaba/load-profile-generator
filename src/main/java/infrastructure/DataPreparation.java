package infrastructure;

import domain.transform.calculations.DateEqualCalculations;
import domain.transform.calculations.NumberEqualCalculations;
import domain.transform.calculations.StringEqualCalculations;
import domain.in.config.ConfigHolder;
import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.in.field.options.OptionsNumber;
import domain.in.field.options.OptionsString;
import domain.out.field.Output;

import java.time.LocalDateTime;
import java.util.ArrayList;

// TODO: I don't understand this class. Is it still necessary?
public class DataPreparation {
  private static final String INTEGER_TYPE = "integer";
  private static final String DECIMAL_TYPE = "decimal";

  private ConfigHolder configGenerator;
  // private int totalData;

  public DataPreparation(ConfigHolder configGenerator) {
    this.configGenerator = configGenerator;
  }

  public ArrayList<Output> prepareData(int cycle) {
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
        Output<String> output =
            new Output<>(inputField.getId(), "string", stringEqualCalculations.calculate());
        data.add(output);

      } else if (inputField.getOptions().getClass() == OptionsNumber.class) {

        OptionsNumber optionsNumber = (OptionsNumber) inputField.getOptions();
        NumberEqualCalculations numberEqualCalculations =
            new NumberEqualCalculations(optionsNumber.getRanges());

        /*
        for (NumberRange range : optionsNumber.getRanges()) {

          System.out.println(range.getMin() + " - " + range.getMax());
        }
        */

        switch (optionsNumber.getType()) {
          case INTEGER_TYPE:
            int iResult = numberEqualCalculations.calculate().intValue();
            Output<Integer> outputInt = new Output<>(inputField.getId(), "integer", iResult);
            data.add(outputInt);
            break;

          case DECIMAL_TYPE:
            float fResult = numberEqualCalculations.calculate();
            Output<Float> outputFloat = new Output<>(inputField.getId(), "decimal", fResult);
            data.add(outputFloat);
            break;
        }

      } else if (inputField.getOptions().getClass() == OptionsDate.class) {

        OptionsDate optionsDate = (OptionsDate) inputField.getOptions();
        DateEqualCalculations dateEqualCalculations =
            new DateEqualCalculations(
                optionsDate.getStartingDate(),
                optionsDate.getTimeIncrement(),
                cycle);

        Output<LocalDateTime> output = new Output<>(inputField.getId(), "date", dateEqualCalculations.calculate());
        data.add(output);
      }

    }

    return data;
  }
}
