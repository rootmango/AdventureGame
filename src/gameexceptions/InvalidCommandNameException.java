package gameexceptions;

public class InvalidCommandNameException extends RuntimeException {
    public InvalidCommandNameException(String message) {
        super(message);
    }
    public InvalidCommandNameException() {}
}
