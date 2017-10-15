package domain.transform.rule;

import domain.value.Value;
import domain.in.rule.ConditionModifier;
import domain.transform.rule.operation.RulesOperationDate;
import domain.transform.rule.operation.RulesOperationFloat;
import domain.transform.rule.operation.RulesOperationInteger;
import domain.transform.rule.operation.RulesOperationString;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RulesApplication {
  private ConditionModifier conditionModifier;
  private String idRule;

  public RulesApplication(String idRule, ConditionModifier conditionModifier) {
    this.idRule = idRule;
    this.conditionModifier = conditionModifier;
  }

  public void applyRules(ArrayList<Value> outputs) {
    for (int i = 0; i < outputs.size(); i++) {
      if (idRule.equals(outputs.get(i).getId())) {
        System.out.println(
            "Changes will be made to output with value " + outputs.get(i).getValue());

        if (outputs.get(i).getValue() instanceof Integer) {
          RulesOperationInteger rulesOperation =
              new RulesOperationInteger(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<Integer>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof String) {
          RulesOperationString rulesOperation =
              new RulesOperationString(
                  conditionModifier.getOperation(), (String) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<String>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof LocalDateTime) {
          RulesOperationDate rulesOperation =
              new RulesOperationDate(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<LocalDateTime>) outputs.get(i));

        } else if (outputs.get(i).getValue() instanceof Float) {
          RulesOperationFloat rulesOperation =
              new RulesOperationFloat(
                  conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<Float>) outputs.get(i));

        }
        /*
        RulesOperationInteger rulesOperation =
                new RulesOperationInteger(
                        conditionModifier.getOperation(), conditionModifier.getValue());
        rulesOperation.applyChanges((Value<Integer>) outputs.get(i));
        */
        System.out.println(
            "Found id with value " + outputs.get(i).getValue() + ". Changing value. ");
      }
    }
  }
}
