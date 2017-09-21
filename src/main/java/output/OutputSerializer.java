package output;

import config.Options;


public interface OutputSerializer<T extends Options, R> {

    R write(T option);

}
