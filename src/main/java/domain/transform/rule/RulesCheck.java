package domain.transform.rule;

import domain.in.rule.InputRule;
import domain.transform.rule.create.CreateRuleLocalDateTime;
import domain.transform.rule.create.CreateRuleNumber;
import domain.transform.rule.create.CreateRuleString;
import domain.value.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class that we can use to check if the rule can be applied or not.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class RulesCheck {
  /** List of domain.rules that we obtain from the config file. */
  private List<InputRule> rules;

  /**
   * Constructor.
   *
   * @param rules List of domain.rules from the config file.
   */
  public RulesCheck(List<InputRule> rules) {
    this.rules = rules;
  }

  /**
   * Method that given a list of values that form a complete data, will check if any rule must be
   * applied, and if it does, applies the changes that tbe rule mentions.
   *
   * @param outputs List of values that form a data.
   * @return Returns the list of values, changed or not depending if the any rule applies or not.
   */
  public List<Value> applyRules(List<Value> outputs) {
    for (InputRule rule : this.rules) {

      for (Value output : outputs) {

        if (rule.getId().equals(output.getId())) {
          boolean resultsChecked = false;

          if (output.getValue() instanceof Float) {
            resultsChecked =
                (new CreateRuleNumber())
                    .getCondition((Value<Float>) output, (InputRule<Double>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof String) {
            resultsChecked =
                (new CreateRuleString())
                    .getCondition((Value<String>) output, (InputRule<String>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof LocalDateTime) {

            InputRule<LocalDateTime> _inputRule =
                ((InputRule<String>) rule)
                    .map(
                        (dateAsString) ->
                            LocalDateTime.parse(
                                dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

            resultsChecked =
                (new CreateRuleLocalDateTime())
                    .getCondition((Value<LocalDateTime>) output, _inputRule)
                    .checkResults();
          }

          /* If the value fulfills a condition specified by the rule, it wil apply the changes. */
          if (resultsChecked) {
            RulesApplication rulesApplication =
                new RulesApplication(rule.getId(), rule.getResult());
            rulesApplication.applyRules(outputs);
          }
        }
      }
    }

    return outputs;
  }
}
