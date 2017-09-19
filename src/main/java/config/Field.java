package config;

public class Field<Opt> {
    private int id;
    private String name;
    private String type;
    private FieldInfo fieldInfo;
    private Opt options;

    public Field(){}

    public Field(FieldInfo fieldInfo, Opt options){
        /*this.id = id;
        this.name = name;
        this.type = type;*/
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

    public void setOptions(Opt options) {
        this.options = options;
    }
}
