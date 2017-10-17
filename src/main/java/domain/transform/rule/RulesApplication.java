package domain.transform.rule;

import domain.value.Value;
import domain.in.rule.ConditionModifier;
import domain.transform.rule.operation.RulesOperationDate;
import domain.transform.rule.operation.RulesOperationNumber;
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
    for (Value output : outputs) {
      if (idRule.equals(output.getId())) {
        System.out.println(
                "Changes will be made to output with value " + output.getValue());

        if (output.getValue() instanceof String) {
          RulesOperationString rulesOperation =
                  new RulesOperationString(
                          conditionModifier.getOperation(), (String) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<String>) output);

        } else if (output.getValue() instanceof LocalDateTime) {
          RulesOperationDate rulesOperation =
                  new RulesOperationDate(
                          conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<LocalDateTime>) output);

        } else if (output.getValue() instanceof Float) {
          RulesOperationNumber rulesOperation =
                  new RulesOperationNumber(
                          conditionModifier.getOperation(), (Double) conditionModifier.getValue());

          rulesOperation.applyChanges((Value<Float>) output);

        }

        System.out.println(
                "Found id with value " + output.getValue() + ". Changing value. ");
      }
    }
  }
}
