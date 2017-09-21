package output;

import config.Field;

/**
 * Interface used for generating a string based on the type of field and the type of output we want.
 */
public interface OutputSerializer {

    /**
     * Function that will generate a String depending on the field and the type of output desired.
     * @param field Field with the values that will be added to the String.
     * @param outputType String with the desired type. Compatible with JSON format.
     * @return String following the format of the specified type.
     */
    String write(Field field, String outputType);

}
