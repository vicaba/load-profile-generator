package domain.transform.rule.operation;

import domain.value.Value;

public interface RulesOperationT<T> {

    void applyChanges(Value<T> output);
}
