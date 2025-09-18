package gameexceptions;

import java.io.IOException;

/**
 * A subclass of IOException that specifically signifies something
 * is wrong with the game's files
 */
public class GameIOException extends IOException {
    public GameIOException(String message) {
        super(message);
    }
    public GameIOException() {}
}
