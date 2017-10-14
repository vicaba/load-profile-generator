package domain.transform.rule;

import domain.out.field.Output;
import domain.in.rules.InputRule;
import domain.transform.rule.create.CreateRuleFloat;
import domain.transform.rule.create.CreateRuleInteger;
import domain.transform.rule.create.CreateRuleLocalDateTime;
import domain.transform.rule.create.CreateRuleString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RulesCheck {
  private ArrayList<InputRule> rules;

  public RulesCheck(ArrayList<InputRule> rules) {
    this.rules = rules;
  }

  public void applyRules(ArrayList<Output> outputs) {
    for (InputRule rule : this.rules) {

      for (Output output : outputs) {

        if (rule.getId().equals(output.getId())) {
          boolean resultsChecked = false;

          if (output.getValue() instanceof Integer) {
            resultsChecked =
                (new CreateRuleInteger()).getCondition((Output<Integer>) output, (InputRule<Double>) rule).checkResults();

          } else if (output.getValue() instanceof Float) {
            resultsChecked =
                (new CreateRuleFloat())
                    .getCondition((Output<Float>) output, (InputRule<Double>) rule)
                    .checkResults();

          } else if (output.getValue() instanceof String) {
            resultsChecked =
                (new CreateRuleString())
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
                (new CreateRuleLocalDateTime())
                    .getCondition((Output<LocalDateTime>) output, _inputRule)
                    .checkResults();
          }

          if (resultsChecked) {
            RulesApplication rulesApplication = new RulesApplication(rule.getId(),rule.getResult());
            rulesApplication.applyRules(outputs);

          }
        }
      }
    }
    for (Output output : outputs) {
      System.out.println("Output value is " + output.getValue());
    }
  }
}
