package infrastructure.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that used to generate the BufferedReader to read a file.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class BufferedFileReader {
  /** The BufferedReader we will use to read a file. */
  private BufferedReader bufferedReader;
  /** The pathname of the file. */
  private String filePath;

  /**
   * Constructor.
   *
   * @param filePath String the with pathname of the file.
   */
  public BufferedFileReader(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Method that generates a new BufferedReader based on the pathname of the file.
   *
   * @return Returns the generated BufferedReader.
   */
  public BufferedReader getReader() {
    try {

      this.bufferedReader = new BufferedReader(new FileReader(this.filePath));
      return this.bufferedReader;
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    }
    return null;
  }

  /** Method used to close the BufferedReader once we're done with it. */
  public void closeReader() {
    try {

      this.bufferedReader.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}
