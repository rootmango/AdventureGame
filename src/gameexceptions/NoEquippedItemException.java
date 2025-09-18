package gameexceptions;

public class NoEquippedItemException extends RuntimeException {
    public NoEquippedItemException(String message) {
        super(message);
    }
    public NoEquippedItemException() { }
}
