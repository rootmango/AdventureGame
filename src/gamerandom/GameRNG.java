package gamerandom;

import java.util.Random;

public class GameRNG {
    /**
     * A wrapper method for {@code java.util.Random} that returns a random {@code int} in the specified range.
     *
     * @param lowerBound (inclusive)
     * @param upperBound (inclusive)
     * @throws IllegalArgumentException if {@code lowerBound > upperBound}
     */
    public static int randomInRange(int lowerBound, int upperBound) throws IllegalArgumentException {
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException();
        } else if (lowerBound == upperBound) {
            return lowerBound;
        } else {
            Random random = new Random();
            return random.nextInt((upperBound - lowerBound) + 1) + lowerBound;
        }
    }
}
