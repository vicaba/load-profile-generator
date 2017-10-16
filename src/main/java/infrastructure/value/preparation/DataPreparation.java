package infrastructure.value.preparation;

import domain.in.config.ConfigHolder;
import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.in.field.options.OptionsNumber;
import domain.in.field.options.OptionsString;
import domain.transform.calculations.DateEqualCalculations;
import domain.transform.calculations.NumberEqualCalculations;
import domain.value.Value;

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

  public ArrayList<Value> prepareData(int cycle) {
    ArrayList<InputField> inputFields = this.configGenerator.getFields();
    ArrayList<Value> data = new ArrayList<>();

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
        DataStringPreparation dataPreparation = new DataStringPreparation(inputField);
        data.add(dataPreparation.obtainPreparedValue());

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
            Value<Integer> outputInt = new Value<>(inputField.getId(), "integer", iResult);
            data.add(outputInt);
            break;

          case DECIMAL_TYPE:
            float fResult = numberEqualCalculations.calculate();
            Value<Float> outputFloat = new Value<>(inputField.getId(), "decimal", fResult);
            data.add(outputFloat);
            break;
        }

      } else if (inputField.getOptions().getClass() == OptionsDate.class) {

        OptionsDate optionsDate = (OptionsDate) inputField.getOptions();
        DateEqualCalculations dateEqualCalculations =
            new DateEqualCalculations(
                optionsDate.getStartingDate(), optionsDate.getTimeIncrement(), cycle);

        Value<LocalDateTime> output =
            new Value<>(inputField.getId(), "date", dateEqualCalculations.calculate());
        data.add(output);
      }
    }

    return data;
  }
}
