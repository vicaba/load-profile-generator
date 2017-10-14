package domain.transform.rule.operation;

import domain.out.field.Output;

public interface RulesOperationT<T> {

    void applyChanges(Output<T> output);
}
