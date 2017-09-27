package output.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileWriter;

public class JsonFileOutput implements FileOutput<JsonArray> {

    @Override
    public void writeToFile(JsonArray dataSet) {

        FileWriterTreatment fileWriterTreatment = new FileWriterTreatment();
        FileWriter jsonWriter = fileWriterTreatment.createFile();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(dataSet, jsonWriter);

        fileWriterTreatment.closeFile();
    }
}
