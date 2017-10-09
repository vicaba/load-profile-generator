package output.rule;

import domain.output.Output;
import domain.rules.InputRule;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RulesApplication {
  private ArrayList<InputRule> rules;

  public RulesApplication(ArrayList<InputRule> rules) {
    this.rules = rules;
  }

  public void applyRules(ArrayList<Output> outputs) {
    for (InputRule rule : this.rules) {
      for (Output output : outputs) {
        if (rule.getId().equals(output.getId())) {
          boolean resultsChecked = false;
          if (output.getValue() instanceof Integer) {
            Integer leftCondition = (Integer) output.getValue();
            Double rightCondition = (Double) rule.getComparator();

            RulesCondition<Integer> rulesCondition =
                new RulesCondition<>(leftCondition, rule.getCondition(), rightCondition.intValue());
            resultsChecked = rulesCondition.checkResults();

          } else if (output.getValue() instanceof Float) {
            Float leftCondition = (Float) output.getValue();
            Double rightCondition = (Double) rule.getComparator();

            RulesCondition<Float> rulesCondition =
                new RulesCondition<>(
                    leftCondition, rule.getCondition(), rightCondition.floatValue());
            resultsChecked = rulesCondition.checkResults();

          } else if (output.getValue() instanceof String) {
            String leftCondition = (String) output.getValue();
            String rightCondition = (String) rule.getComparator();

            RulesCondition<String> rulesCondition =
                new RulesCondition<>(leftCondition, rule.getCondition(), rightCondition);
            resultsChecked = rulesCondition.checkResults();

          } else if (output.getValue() instanceof LocalDateTime) {
              Long leftCondition = ((LocalDateTime) output.getValue())
                      .atZone(ZoneId.systemDefault())
                      .toInstant()
                      .toEpochMilli();
              Long rightCondition =
                      LocalDateTime
                              .parse((String)rule.getComparator(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                              .atZone(ZoneId.systemDefault())
                              .toInstant()
                              .toEpochMilli();

              RulesCondition<Long> rulesCondition =
                      new RulesCondition<>(leftCondition, rule.getCondition(), rightCondition);
              resultsChecked = rulesCondition.checkResults();

          }
          if (resultsChecked) {
            System.out.println(
                "Found a number in " + output.getValue() + ". Apply rule " + rule.getComparator());
          } else {
              System.out.println(
                      "Didn't find a number in " + output.getValue() + ". Apply rule " + rule.getComparator());
          }
        }
      }
    }
  }
}
