package config;

import java.util.ArrayList;

public class OptionsNumber extends Options {
    private class Range {
        private int min;
        private boolean exclusiveMin;
        private int max;
        private boolean exclusiveMax;
    }
    private ArrayList<Range> ranges;

}
