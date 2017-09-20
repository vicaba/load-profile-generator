package output;

import config.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OutputText {
    private ConfigGenerator genC;
    private Random randomSeed;
    private static final String FILE_OUTPUT = "output/data";
    private static final String FILE_FORMAT = ".xml";
    private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";

    public OutputText(ConfigGenerator genC, Random randomSeed) {
        this.genC = genC;
        this.randomSeed = randomSeed;
    }

    public void generateFileOutput(int iTotalData) {
        try {

            String timeLog = new SimpleDateFormat(DATE_PATTERN).format(
                            Calendar.getInstance().getTime());
            File logFile = new File(FILE_OUTPUT+timeLog+FILE_FORMAT);
            if (logFile.createNewFile()) {
                FileOutputStream xmlOut = new FileOutputStream(logFile, false);

                writeDataToOutput(iTotalData, xmlOut);
                xmlOut.close();
            }

            //writer.close();
        } catch (IOException | ParseException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private void writeDataToOutput(int iTotalData, FileOutputStream xmlOut)
            throws IOException, ParseException, JAXBException {

        ArrayList<Field> fields = genC.getFields();
        OutputEntity outputEntity = new OutputEntity();
        OutputData outputData;

        JAXBContext contextObj = JAXBContext.newInstance(OutputEntity.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        for (int i = 0; i < iTotalData; i++) {
            outputData = new OutputData();
            for (Field field: fields) {
                System.out.println(field.getId()+"-"+field.getName()+"-"+field.getType()+"-"+field.getOptions());

                if (field.getOptions().getClass() == OptionsString.class) {
                    OptionsString optString = (OptionsString) field.getOptions();
                    OutputTypeString outputTypeString = new OutputTypeString();
                    outputTypeString.fillDataValues(optString,randomSeed);
                    outputData.setOutputType(outputTypeString);

                } else if (field.getOptions().getClass() == OptionsNumber.class) {
                    OptionsNumber optNumber = (OptionsNumber) field.getOptions();
                    OutputTypeNumber outputTypeNumber = new OutputTypeNumber();
                    outputTypeNumber.fillDataValues(optNumber,randomSeed);
                    outputData.setOutputType(outputTypeNumber);

                } else if (field.getOptions().getClass() == OptionsDate.class) {
                    OptionsDate optDate = (OptionsDate) field.getOptions();
                    OutputTypeDate outputTypeDate = new OutputTypeDate();
                    outputTypeDate.fillDataValues(optDate,i);
                    outputData.setOutputType(outputTypeDate);
                }

            }
            outputEntity.setOutputData(outputData);
        }

        marshallerObj.marshal(outputEntity,xmlOut);
    }


}
