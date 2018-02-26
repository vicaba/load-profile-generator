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

/**
 * Class that we can use to pass to apply the stream.rules when RulesCheck confirms that they must be
 * applied.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class RulesApplication {
  /** Constant that defines the name of the logger used for printing debug messages: {@value} */
  private static final String LOGGER_NAME = "app.logger";

  /** Object that contains the info necessary to make changes to the values. */
  private ConditionModifier conditionModifier;
  /** Id of the node that we need to apply the changes. */
  private String idRule;
  /** The Logger we use for debugging purposes. */
  private Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

  /**
   * Constructor.
   *
   * @param idRule String with the id of the node that we will have to apply the changes.
   * @param conditionModifier Object with all the information needed to apply the changes.
   */
  public RulesApplication(String idRule, ConditionModifier conditionModifier) {
    this.idRule = idRule;
    this.conditionModifier = conditionModifier;
  }

  /**
   * Method used to apply the rule to the values that apply that rule.
   *
   * @param outputs The list of values that form a data.
   */
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
