package CustomExceptions;

public class EmptySerieException extends Exception {
    public EmptySerieException() {
        super("Cannot perform the given operation on an empty serie");
    }
}
