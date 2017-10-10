package output.rule;

import domain.output.Output;
import domain.rules.ConditionModifier;
import output.rule.operation.RulesOperationDate;
import output.rule.operation.RulesOperationFloat;
import output.rule.operation.RulesOperationInteger;
import output.rule.operation.RulesOperationString;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RulesApplication {
  private ConditionModifier conditionModifier;
  private String idRule;

  public RulesApplication(String idRule, ConditionModifier conditionModifier) {
    this.idRule = idRule;
    this.conditionModifier = conditionModifier;
  }

  public void applyRules(ArrayList<Output> outputs) {
    for (int i = 0; i < outputs.size(); i++) {
      if (idRule.equals(outputs.get(i).getId())) {
        System.out.println(
            "Changes will be made to output with value " + outputs.get(i).getValue());

        if (outputs.get(i).getValue() instanceof Integer) {
          RulesOperationInteger rulesOperation =
              new RulesOperationInteger(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Output<Integer>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof String) {
          RulesOperationString rulesOperation =
              new RulesOperationString(
                  conditionModifier.getOperation(), (String) conditionModifier.getValue());

          rulesOperation.applyChanges((Output<String>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof LocalDateTime) {
          RulesOperationDate rulesOperation =
              new RulesOperationDate(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Output<LocalDateTime>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof Float) {
          RulesOperationFloat rulesOperation =
              new RulesOperationFloat(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Output<Float>) outputs.get(i));

        }
        /*
        RulesOperationInteger rulesOperation =
                new RulesOperationInteger(
                        conditionModifier.getOperation(), conditionModifier.getValue());
        rulesOperation.applyChanges((Output<Integer>) outputs.get(i));
        */
        System.out.println(
            "Found id with value " + outputs.get(i).getValue() + ". Changing value. ");
      }
    }
  }
}
