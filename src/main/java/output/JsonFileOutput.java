package output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import config.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class JsonFileOutput implements FileOutput<JsonArray> {
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";
    private static final String FILE_OUTPUT = "output/data";
    private static final String FILE_FORMAT = ".json";

    @Override
    public void writeToFile(JsonArray dataSet) {
        String timeLog = new SimpleDateFormat(DATE_PATTERN).format(
                Calendar.getInstance().getTime());
        File logFile = new File(FILE_OUTPUT+timeLog+FILE_FORMAT);
        try {
            if (logFile.createNewFile()) {
                FileWriter jsonWriter = new FileWriter(logFile);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(dataSet, jsonWriter);
                jsonWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JsonArray prepareData(ConfigGenerator genC, int totalData, OutputCalculations outputCalculations) {
        ArrayList<Field> fields = genC.getFields();
        JsonArray dataSet = new JsonArray();
        JsonArray data;

        for (int i = 0; i < totalData; i++) {
            data = new JsonArray();
            for (Field field: fields) {
                System.out.println(field.getId()+"-"+field.getName()+"-"+field.getType()+"-"+field.getOptions());

                if (field.getOptions().getClass() == OptionsString.class) {
                    //TODO If added in the future, there will be a switch to decide the method used. This will most likely be inside a config file.
                    StringJsonOutputSerializer sJOS = new StringJsonOutputSerializer(outputCalculations);
                    OptionsString optionsString = (OptionsString) field.getOptions();
                    data.add(sJOS.write(optionsString));

                } else if (field.getOptions().getClass() == OptionsNumber.class) {
                    NumberJsonOutputSerializer nJOS = new NumberJsonOutputSerializer(outputCalculations);
                    OptionsNumber optionsNumber = (OptionsNumber) field.getOptions();
                    data.add(nJOS.write(optionsNumber));

                } else if (field.getOptions().getClass() == OptionsDate.class) {
                    DateJsonOutputSerializer dJOS = new DateJsonOutputSerializer(outputCalculations, i);
                    OptionsDate optionsDate = (OptionsDate) field.getOptions();
                    data.add(dJOS.write(optionsDate));
                }

            }
            dataSet.add(data);
        }

        return dataSet;
    }
}
