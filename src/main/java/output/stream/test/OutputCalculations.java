package output.stream.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Class where all the calculations are made. Can work with integers, decimals or dates.
 * @author Albert Trias Torroglosa
 * @since 21/09/2017
 * @version 1.0
 */
//TODO Create different classes according to the method used for probability.
public class OutputCalculations {
    private final static char EQUIV = 'E'; //Random method will give the same probability to all numbers.

    private Random randomSeed;             //Random class with the generated seed for EQUIV random method.

    /**
     * Constructor that will generate the random seed when it's used.
     */
    public OutputCalculations() {
        randomSeed = new Random();
    }

    /**
     * Function that by giving a range of numbers and a method it will return a random integer between them.
     * @param min int Inclusive Min value.
     * @param max int Inclusive or Exclusive Max value, depending on the method.
     * @param method Random method to be used.
     * @return int with a value between the range given.
     */
    public int calculateRangeInteger(int min, int max, char method) {
        switch(method) {
            case EQUIV:
                return equalChanceInteger(min, max);
        }
        return -1;
    }

    /**
     * Function that by giving a range of numbers and a method it will return a random decimal between them.
     * @param min float Inclusive Min value.
     * @param max float Inclusive or Exclusive Max value, depending on the method.
     * @param method char with Random method to be used.
     * @return float with a value between the range given.
     */
    public float calculateRangeDecimal(float min, int max, char method) {
        switch(method) {
            case EQUIV:
                return equalChanceDecimal(min, max);
        }
        return -1;
    }

    /**
     * Function that grabs a starting date and returns the next date based on the cycle and time it passes for each.
     * @param startingDate String with the starting date.
     * @param timeIncrement int with the seconds that must be added for each cycle.
     * @param cycle int with the current data cycle.
     * @param dateFormat String with the format that the starting date and the resulting date follow.
     * @return String with the calculated date.
     */
    public String calculateNextDate(String startingDate, int timeIncrement, int cycle, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime formatDateTime = LocalDateTime.parse(startingDate, formatter)
                .plusSeconds(timeIncrement*cycle);

        return formatDateTime.format(formatter);
    }

    /**
     * Function that will generate a random integer between a range with the same probability for all numbers.
     * @param min int Inclusive Min value.
     * @param max int Inclusive Max value.
     * @return int value between min and max.
     */
    private int equalChanceInteger(int min, int max) {
        return randomSeed.nextInt( max + 1 - min) + min;
    }

    /**
     * Function that will generate a random decimal between a range with the same probability for all numbers.
     * @param min float Inclusive Min value.
     * @param max float Exclusive Max value.
     * @return float value between min and max.
     */
    private float equalChanceDecimal(float min, float max) {
        return randomSeed.nextFloat() * (max - min) + min;
    }
}
