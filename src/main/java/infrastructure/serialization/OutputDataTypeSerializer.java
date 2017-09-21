package infrastructure.serialization;

import output.OutputType;

/**
 *
 * @param <T> The type of DataType
 */
public interface OutputDataTypeSerializer<T extends OutputType, R> {
  R write(T o);
}

