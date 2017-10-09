package output.rule;

import domain.output.Output;
import domain.rules.InputRule;

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
            RulesCondition rulesCondition =
                    new RulesCondition<>(output.getValue(),rule.getCondition(), rule.getComparator());
            if (true) {
                System.out.println("Found a number in " + output.getValue() +". Apply rule " + rule.getComparator());
            }
        }
      }
    }
  }

}
