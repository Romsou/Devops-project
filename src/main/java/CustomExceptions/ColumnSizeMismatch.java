package CustomExceptions;

public class ColumnSizeMismatch extends Exception {
    public ColumnSizeMismatch() {
        super("Array size differ with DataFrame size");
    }
}
