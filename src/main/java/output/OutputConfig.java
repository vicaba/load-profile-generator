package output;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OutputConfig {
    private int amount;
    private String method;
    private String type;

    private final static String AMOUNT_FIELD = "amount";
    private final static String METHOD_FIELD = "method";
    private final static String TYPE_FIELD = "type";
    private final static String FILE_VALUE = "file";
    private static final String JSON_PATH = "resources/output.json";

    public OutputConfig() {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(JSON_PATH));
            JsonObject jsonObject = new Gson().fromJson(br,JsonObject.class);

            this.amount = jsonObject.get(AMOUNT_FIELD).getAsInt();
            this.method = jsonObject.get(METHOD_FIELD).getAsString();
            if (this.method.equals(FILE_VALUE)) {
                this.type = jsonObject.get(TYPE_FIELD).getAsString();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
