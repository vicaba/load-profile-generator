package config;

public class Field<Opt> {
    private FieldInfo fieldInfo;
    private Opt options;

    Field(FieldInfo fieldInfo, Opt options){
        this.fieldInfo = fieldInfo;
        this.options = options;
    }

    public int getId() { return this.fieldInfo.getId(); }

    public String getName() {
        return this.fieldInfo.getName();
    }

    public String getType() {
        return this.fieldInfo.getType();
    }

    public Opt getOptions() {
        return this.options;
    }

}
