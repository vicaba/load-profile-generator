package output;

import config.OptionsNumber;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Random;

@XmlRootElement(name = "number")
public class OutputTypeNumber extends OutputType {
    OutputTypeNumber() {
        super();
    }

    void fillDataValues(OptionsNumber optionsNumber, Random randomSeed) {
        int iRange = randomSeed.nextInt(optionsNumber.getTotalRanges());
        int iMin = optionsNumber.getRangeMin(iRange);

        type = "number";
        data = Integer.toString(
                randomSeed.nextInt(optionsNumber.getRangeMax(iRange) + 1 - iMin) + iMin);
    }

    //TODO Compatibility with decimals
    //TODO Consider removing exclusive and inclusive fields and just make it inclusive min and exclusive max
}
