package output.data;

public interface OutputSerializer<T extends String, R> {

  R write(T output);
}
