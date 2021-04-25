package CustomExceptions;

public class EmptyArrayException extends Exception {
    public EmptyArrayException() {
        super("Cannot perform the given operation on an empty array");
    }
}
