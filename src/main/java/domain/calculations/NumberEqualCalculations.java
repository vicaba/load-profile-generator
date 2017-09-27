package domain.calculations;

import config.Range;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NumberEqualCalculations implements EqualCalculations<Float> {
    private ArrayList<Range> ranges;

    public NumberEqualCalculations(ArrayList<Range> ranges) {
        this.ranges = ranges;
    }

    @Override
    public Float calculate() {
        Range range = ranges.get(ThreadLocalRandom.current().nextInt(0, ranges.size()));

        float fMin = range.getMin();
        return ThreadLocalRandom.current().nextFloat() * (range.getMax() - fMin) + fMin;
    }
}
