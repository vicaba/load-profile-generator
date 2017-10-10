package output.rule.operation;

import domain.output.Output;

public interface RulesOperationT<T> {

    void applyChanges(Output<T> output);
}
