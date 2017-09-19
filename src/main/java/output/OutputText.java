package output;

import config.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OutputText {
    private ConfigGenerator genC;
    private Random randomSeed;
    private static final String FILE_OUTPUT = "output/data";
    private static final String FILE_FORMAT = ".out";
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

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
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void writeDataToOutput(int iTotalData, BufferedWriter writer) throws IOException, ParseException {
        ArrayList<Field> fields = genC.getFields();
        for (int i = 0; i < iTotalData; i++) {
            for (Field field: fields) {
                System.out.println(field.getId()+"-"+field.getName()+"-"+field.getType()+"-"+field.getOptions());

                if (field.getOptions().getClass() == OptionsString.class) {
                    OptionsString optString = (OptionsString) field.getOptions();
                    writeRandomString(writer, optString);

                } else if (field.getOptions().getClass() == OptionsNumber.class) {
                    OptionsNumber optNumber = (OptionsNumber) field.getOptions();
                    writeRandomNumber(writer, optNumber);
                } else if (field.getOptions().getClass() == OptionsDate.class) {
                    OptionsDate optDate = (OptionsDate) field.getOptions();
                    writeNextDate(writer, optDate, i);
                }

                writer.write(" ");
            }
            writer.write("\n");
        }
    }

    private void writeRandomString(BufferedWriter writer, OptionsString optionsString) throws IOException {
        writer.write(optionsString.getAcceptedString(
                randomSeed.nextInt(optionsString.getSizeAccepted())
        ));
    }

    //TODO Compatibility with decimals
    //TODO Consider removing exclusive and inclusive fields and just make it inclusive min and exclusive max
    private void writeRandomNumber(BufferedWriter writer, OptionsNumber optionsNumber) throws IOException {
        int iRange = randomSeed.nextInt(optionsNumber.getTotalRanges());
        int iMin = optionsNumber.getRangeMin(iRange);
        writer.write(Integer.toString(
                randomSeed.nextInt(optionsNumber.getRangeMax(iRange) + 1 - iMin) + iMin)
        );
    }

    private void writeNextDate(BufferedWriter writer, OptionsDate optionsDate, int iCycle) throws ParseException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        LocalDateTime formatDateTime = LocalDateTime.parse(optionsDate.getStartingDate(), formatter)
                .plusSeconds(optionsDate.getTimeIncrement()*iCycle);

        writer.write(formatDateTime.format(formatter));
    }

}
