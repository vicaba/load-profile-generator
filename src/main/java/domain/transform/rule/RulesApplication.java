package domain.transform.rule;

import domain.in.condition.ConditionModifier;
import domain.transform.rule.operation.RulesOperationDate;
import domain.transform.rule.operation.RulesOperationNumber;
import domain.transform.rule.operation.RulesOperationString;
import domain.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class RulesApplication {
  private ConditionModifier conditionModifier;
  private String idRule;
  private final Logger logger = LoggerFactory.getLogger("app.logger");

  public RulesApplication(String idRule, ConditionModifier conditionModifier) {
    this.idRule = idRule;
    this.conditionModifier = conditionModifier;
  }

  public void applyRules(List<Value> outputs) {
    for (Value output : outputs) {
      if (idRule.equals(output.getId())) {
        logger.debug("Changes will be made to output with value " + output.getValue());

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

        logger.debug("Found id with value " + output.getValue() + ". Changing value. ");
      }
    }
  }
}
