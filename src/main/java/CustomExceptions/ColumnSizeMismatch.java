package CustomExceptions;

public class ColumnSizeMismatch extends Exception {
    public ColumnSizeMismatch() {
        super("Array size differs from DataFrame size");
    }
}
