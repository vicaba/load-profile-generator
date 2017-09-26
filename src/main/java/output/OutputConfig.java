package output;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OutputConfig {
    private int amount;
    private String method;
    private String type;

    private static final String JSON_PATH = "resources/output.json";

    public boolean configureFromJson() {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(JSON_PATH));
            GsonBuilder gsonBuilder = new GsonBuilder();
            OutputConfig outputConfig = gsonBuilder.create()
                    .fromJson(br, OutputConfig.class);

            this.amount = outputConfig.getAmount();
            this.method = outputConfig.getMethod();
            this.type = outputConfig.getType();

            //TODO Remove testing print
            System.out.println(gsonBuilder.create().toJson(outputConfig));

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getMethod() {
        return this.method;
    }

    public String getType() {
        return this.type;
    }
}
