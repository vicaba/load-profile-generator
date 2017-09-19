package config;

import java.util.ArrayList;

public class OptionsNumber extends Options {
    private class Range {
        private int min;
        private boolean exclusiveMin;
        private int max;
        private boolean exclusiveMax;

        int getMin() {
            return this.min;
        }

        int getMax() {
            return this.max;
        }
    }
    private ArrayList<Range> ranges;

    public OptionsNumber() {
        ranges = new ArrayList<>();
    }

    public int getTotalRanges() {
        return ranges.size();
    }

    public int getRangeMin(int iRange) {
        return this.ranges.get(iRange).getMin();
    }

    public int getRangeMax(int iRange) {
        return this.ranges.get(iRange).getMax();
    }

}
