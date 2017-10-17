package infrastructure.value.preparation;

import domain.in.config.ConfigHolder;
import domain.in.field.InputField;
import domain.in.field.options.OptionsDate;
import domain.in.field.options.OptionsNumber;
import domain.in.field.options.OptionsString;
import domain.transform.calculations.NumberEqualCalculations;
import domain.transform.calculations.StringEqualCalculations;
import domain.value.Value;

import java.util.ArrayList;

// TODO: I don't understand this class. Is it still necessary?
public class DataPreparation {
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
        OptionsString options = (OptionsString) inputField.getOptions();
        StringValueGeneration dataPreparation =
            new StringValueGeneration(
                inputField, new StringEqualCalculations(options.getAcceptedStrings()));
        data.add(dataPreparation.obtainNext());

      } else if (inputField.getOptions().getClass() == OptionsNumber.class) {

        OptionsNumber optionsNumber = (OptionsNumber) inputField.getOptions();
        NumberEqualCalculations numberEqualCalculations =
            new NumberEqualCalculations(optionsNumber.getRanges());
        float fResult = numberEqualCalculations.calculate();
        Value<Float> outputNumber = new Value<>(inputField.getId(), inputField.getType(), fResult);
        data.add(outputNumber);

      } else if (inputField.getOptions().getClass() == OptionsDate.class) {
        // TODO This no longer works if data is increased inside class.

        // DataGeneration dataPreparation = new DataGeneration(inputField, cycle);
        // data.add(dataPreparation.obtainNext());
      }
    }

    return data;
  }
}
