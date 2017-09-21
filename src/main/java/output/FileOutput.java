package output;

public interface FileOutput<T> extends Output<T> {
    void writeToFile(T dataSet);
}
