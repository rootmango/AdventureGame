package mvc.controllers.game;

import mvc.views.MainView;

import java.util.concurrent.TimeUnit;

public class GameTimeUtils {
    public long elapsedTimeFrom(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    public void printElapsedTime(long startTime, MainView mainView) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeFrom(startTime));
        if (minutes >= 0 && minutes <= 59) {
            mainView.outputln("Play time: " + minutes + " minutes");
        } else if (minutes >= 60) {
            mainView.outputln("Play time: " + (minutes / 60) + " hours and "
                                + (minutes % 60) + " minutes");
        }
        // won't print anything if there's an error with the elapsed time calculations
        // (minutes < 0)
    }
}
