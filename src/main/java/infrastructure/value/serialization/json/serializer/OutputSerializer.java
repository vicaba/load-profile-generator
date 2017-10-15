package infrastructure.value.serialization.json.serializer;

public interface OutputSerializer<T extends String, R> {

  R write(T output);
}
