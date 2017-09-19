package output;

import config.ConfigGenerator;
import config.Field;
import config.OptionsString;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class OutputText {
    private ConfigGenerator genC;
    private Random randomSeed;
    private static final String FILE_OUTPUT = "output/data";
    private static final String FILE_FORMAT = ".out";
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";

    public OutputText(ConfigGenerator genC, Random randomSeed) {
        this.genC = genC;
        this.randomSeed = randomSeed;
    }

    public void generateFileOutput(int iTotalData) {
        BufferedWriter writer;
        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat(DATE_PATTERN).format(
                            Calendar.getInstance().getTime());
            File logFile = new File(FILE_OUTPUT+timeLog+FILE_FORMAT);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile));

            writeDataToOutput(iTotalData, writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDataToOutput(int iTotalData, BufferedWriter writer) throws IOException {
        ArrayList<Field> fields = genC.getFields();
        for (int i = 0; i < iTotalData; i++) {
            for (Field field: fields) {
                System.out.println(field.getId()+"-"+field.getName()+"-"+field.getType()+"-"+field.getOptions());

                if (field.getOptions().getClass() == OptionsString.class) {
                    OptionsString optString = (OptionsString) field.getOptions();
                    writeRandomString(writer, optString);

                }
            }
            writer.write("\n");
        }
    }

    private void writeRandomString(BufferedWriter writer, OptionsString optionsString) throws IOException {
        writer.write(optionsString.getAcceptedString(
                randomSeed.nextInt(optionsString.getSizeAccepted())
        ));
    }

}
