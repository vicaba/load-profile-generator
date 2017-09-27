package domain.field;

public class InputField<Opt> {
    private InputFieldInfo fields;
    private Opt options;

    public InputField(InputFieldInfo inputFieldInfo, Opt options) {
        this.fields = inputFieldInfo;
        this.options = options;
    }

    public int getId() {
        return this.fields.getId();
    }

    public String getName() {
        return this.fields.getName();
    }

    public String getType() {
        return this.fields.getType();
    }

    public Opt getOptions() {
        return this.options;
    }
}
