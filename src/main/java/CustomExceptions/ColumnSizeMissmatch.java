package CustomExceptions;

public class ColumnSizeMissmatch extends Exception {
    public ColumnSizeMissmatch() {
        super("Array size differ with DataFrame size");
    }
}
