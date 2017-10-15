package infrastructure.value.serialization;

public interface ValueSerializer<T extends String, R> {

  R write(T output);
}
