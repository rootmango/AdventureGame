package gameexceptions;

public class EmptyCommandNameException extends RuntimeException {
  public EmptyCommandNameException(String message) {
    super(message);
  }
}
