package CustomExceptions;

public class ColumnNotFoundException extends Exception {
    public ColumnNotFoundException() {
        super("Column with this name doesn't exist");
    }
}
