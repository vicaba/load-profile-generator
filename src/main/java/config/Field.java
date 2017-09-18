package config;

public class Field {
    private int id;
    private String name;
    private String type;
    private Options options;

    public Field(){}

    public Field(int id, String name, String type, Options options){
        this.id = id;
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
