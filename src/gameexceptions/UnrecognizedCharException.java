package gameexceptions;

/**
 * Indicates that there's an unrecognized character in the mvc.controllers.game map.
 */
public class UnrecognizedCharException extends RuntimeException {
    public UnrecognizedCharException(String message) {
        super(message);
    }
    public UnrecognizedCharException() {}
}
