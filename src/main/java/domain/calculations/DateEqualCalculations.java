package domain.calculations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateEqualCalculations implements EqualCalculations<String> {
    private String startingDate;
    private DateTimeFormatter dateTimeFormatter;
    private int timeIncrement;
    private int cycle;

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    public DateEqualCalculations(String startingDate, int timeIncrement, int cycle) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        this.startingDate = startingDate;
        this.timeIncrement = timeIncrement;
        this.cycle = cycle;
    }

    @Override
    public String calculate() {
        LocalDateTime localDateTime =
                LocalDateTime.parse(this.startingDate, this.dateTimeFormatter)
                        .plusSeconds(this.timeIncrement * this.cycle);

        return localDateTime.format(dateTimeFormatter);
    }
}
