package output.rule;

import domain.output.Output;
import domain.rules.InputRule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RulesApplication {
  private ArrayList<InputRule> rules;

  public RulesApplication(ArrayList<InputRule> rules) {
    this.rules = rules;
  }

  @SuppressWarnings("unchecked")
  public void applyRules(ArrayList<Output> outputs) {
    for (InputRule rule : this.rules) {

      for (Output output : outputs) {

        if (rule.getId().equals(output.getId())) {
          boolean resultsChecked = false;

          if (output.getValue() instanceof Integer) {
            resultsChecked =
                (new ApplyRuleInteger())
                    .getCondition((Output<Integer>) output, (InputRule<Double>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof Float) {
            resultsChecked =
                (new ApplyRuleFloat())
                    .getCondition((Output<Float>) output, (InputRule<Double>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof String) {
            resultsChecked =
                (new ApplyRuleString())
                    .getCondition((Output<String>) output, (InputRule<String>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof LocalDateTime) {
            InputRule<LocalDateTime> _inputRule =
                ((InputRule<String>) rule)
                    .map(
                        (dateAsString) ->
                            LocalDateTime.parse(
                                dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

            resultsChecked =
                (new ApplyRuleLocalDateTime())
                    .getCondition((Output<LocalDateTime>) output, _inputRule)
                    .checkResults();
          }

          if (resultsChecked) {
            System.out.println(
                "Found a number in " + output.getValue() + ". Apply rule " + rule.getComparator());

          } else {
            System.out.println(
                "Didn't find a number in "
                    + output.getValue()
                    + ". Apply rule "
                    + rule.getComparator());
          }
        }
      }
    }
  }
}
