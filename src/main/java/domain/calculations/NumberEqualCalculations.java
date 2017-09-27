package domain.calculations;

import domain.options.NumberRange;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NumberEqualCalculations implements EqualCalculations<Float> {
    private ArrayList<NumberRange> numberRanges;

    public NumberEqualCalculations(ArrayList<NumberRange> numberRanges) {
        this.numberRanges = numberRanges;
    }

    @Override
    public Float calculate() {
        NumberRange numberRange =
                this.numberRanges.get(ThreadLocalRandom.current().nextInt(0, this.numberRanges.size()));

        float fMin = numberRange.getMin();
        return ThreadLocalRandom.current().nextFloat() * (numberRange.getMax() - fMin) + fMin;
    }
}
