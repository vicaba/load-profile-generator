package output;

import config.OptionsDate;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlRootElement(name = "date")
class OutputTypeDate extends OutputType {
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    OutputTypeDate() {
        super();
    }

    void fillDataValues(OptionsDate optionsDate, int iCycle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        LocalDateTime formatDateTime = LocalDateTime.parse(optionsDate.getStartingDate(), formatter)
                .plusSeconds(optionsDate.getTimeIncrement()*iCycle);

        type = "date";
        data = formatDateTime.format(formatter);
    }
}
