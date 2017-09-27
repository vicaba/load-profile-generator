package domain.config;

import domain.field.InputField;

import java.util.ArrayList;

public class ConfigGenerator {
    private int length;
    private int skipLine;
    private ArrayList<InputField> fields;

    public ConfigGenerator() {
        fields = new ArrayList<>();
    }

    public int getLength() {
        return this.length;
    }

    public int getSkipLine() {
        return this.skipLine;
    }

    public InputField getField(int field) {
        return fields.get(field);
    }

    public ArrayList<InputField> getFields() {
        return this.fields;
    }
}
