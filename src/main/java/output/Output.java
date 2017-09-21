package output;

import config.*;

public interface Output<T> {
    T prepareData(ConfigGenerator genC, int totalData, OutputCalculations outputCalculations);
}
