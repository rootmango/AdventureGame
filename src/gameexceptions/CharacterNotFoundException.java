package gameexceptions;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String message) {
        super(message);
    }
    public CharacterNotFoundException() {}
}
