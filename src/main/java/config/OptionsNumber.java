package config;

import java.util.ArrayList;

public class OptionsNumber extends Options {
    private String type;
    private ArrayList<Range> ranges;

    public OptionsNumber() {
        this.ranges = new ArrayList<>();
    }

    public ArrayList<Range> getRanges() {
        return this.ranges;
    }

    public String getType() {
        return this.type;
    }
}
