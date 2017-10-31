package domain.in.distribution;

import domain.in.condition.ConditionModifier;

public class InputDistribution {
    private String id;
    private String condition;
    private int comparator;
    private ConditionModifier result;

    public InputDistribution(String id, String condition, int comparator, ConditionModifier result) {
        this.id = id;
        this.condition = condition;
        this.comparator = comparator;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }

    public int getComparator() {
        return comparator;
    }

    public ConditionModifier getResult() {
        return result;
    }

    /*
    public boolean isDistribution(String id) {
        return result.getId().equals(id);
    }
    */
}
