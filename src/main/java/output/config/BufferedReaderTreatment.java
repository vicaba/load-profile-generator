package output.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderTreatment {
  private BufferedReader bufferedReader;
  private String filePath;

  public BufferedReaderTreatment(String filePath) {
    this.filePath = filePath;
  }

  public BufferedReader getReader() {
    try {

      this.bufferedReader = new BufferedReader(new FileReader(this.filePath));
      return this.bufferedReader;
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    }
    return null;
  }

  public void closeReader() {
    try {

      this.bufferedReader.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}
