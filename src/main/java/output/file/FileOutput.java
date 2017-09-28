package output.file;

public interface FileOutput<T> {
  void writeToFile(T dataSet);
}
