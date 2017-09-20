package output;

import config.OptionsString;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Random;

@XmlRootElement(name = "string")
class OutputTypeString extends OutputType {

    OutputTypeString() {
        super();
    }

    void fillDataValues(OptionsString optionsString, Random randomSeed) {
        type = "string";
        data = optionsString.getAcceptedString(
                randomSeed.nextInt(optionsString.getSizeAccepted())
        );
    }
}
