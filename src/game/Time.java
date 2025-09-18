package game;

import mvc.View;

import java.util.concurrent.TimeUnit;

public class Time {
    private static final View view = new View();

    public static long elapsedTimeFrom(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public static void printElapsedTime(long startTime) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeFrom(startTime));
        if (minutes >= 0 && minutes <= 59) {
            view.outputln("Play time: " + minutes + " minutes");
        } else if (minutes >= 60) {
            view.outputln("Play time: " + (minutes / 60) + " hours and "
                                + (minutes % 60) + " minutes");
        }
        // won't print anything if there's an error with the elapsed time calculations
        // (minutes < 0)
    }
}
