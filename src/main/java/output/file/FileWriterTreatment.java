package output.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileWriterTreatment {
  private static final String DATE_PATTERN = "yyyyMMdd_HHmmss";
  private static final String FILE_OUTPUT = "output/data";
  private static final String FILE_FORMAT = ".json";

  private File logFile;
  private FileWriter fileWriter;

  public FileWriterTreatment() {

    String timeLog = new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());
    this.logFile = new File(FILE_OUTPUT + timeLog + FILE_FORMAT);
  }

  public FileWriter createFile() {

    try {

      this.fileWriter = new FileWriter(logFile);
      return this.fileWriter;

    } catch (IOException e) {

      e.printStackTrace();
    }
    return null;
  }

  public void closeFile() {

    try {

      this.fileWriter.close();

    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}
