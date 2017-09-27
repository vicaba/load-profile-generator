package config;

import com.google.gson.GsonBuilder;
import domain.field.Field;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    private static final String JSON_PATH = "resources/config.json";

    public Config(){}

    public ConfigGenerator configureFromJSON() {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(JSON_PATH));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Field.class, new FieldDeserializer());
            ConfigGenerator genC = gsonBuilder.create()
                    .fromJson(br, ConfigGenerator.class);

            System.out.println(gsonBuilder.create().toJson(genC));

            return genC;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
