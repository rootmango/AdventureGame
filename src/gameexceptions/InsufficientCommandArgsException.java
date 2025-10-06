package gameexceptions;

public class InsufficientCommandArgsException extends RuntimeException {
    public InsufficientCommandArgsException(String message) {
        super(message);
    }
    public InsufficientCommandArgsException() {}
}
