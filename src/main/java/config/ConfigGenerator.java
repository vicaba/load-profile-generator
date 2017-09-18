package config;

import java.util.ArrayList;

public class ConfigGenerator {
    private int length;
    private int skipLine;
    private ArrayList<Field> fields;

    public ConfigGenerator() {}

    public int getLength() {
        return this.length;
    }

    public int getSkipLine() {
        return this.skipLine;
    }

    public Field getField(int field) {
        return fields.get(field);
    }
}
