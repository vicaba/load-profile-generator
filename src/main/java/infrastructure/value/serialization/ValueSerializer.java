package infrastructure.value.serialization;

public interface ValueSerializer<T, R> {

  R write(T value);
}
