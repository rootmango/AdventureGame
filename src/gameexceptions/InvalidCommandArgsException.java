package gameexceptions;

public class InvalidCommandArgsException extends RuntimeException {
    public InvalidCommandArgsException(String message) {
        super(message);
    }
}
