package gameexceptions;

public class AnotherItemAlreadyEquippedException extends RuntimeException {
    public AnotherItemAlreadyEquippedException(String message) {
        super(message);
    }
    public AnotherItemAlreadyEquippedException() { }
}
