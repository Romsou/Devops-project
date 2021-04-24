package inference;

public class TypeInferer {

    /**
     * Infers the type of string.
     *
     * This method works by parsing a value passed as a string,
     * creating the proper object out of it and returning it.
     *
     * The limit of type inference being what they are in java,
     * user should first be aware of the type of data they are trying to
     * infer since this method only provides a basic inference mechanism
     *
     * @param value the data we want to infer.
     * @return An object representing our data realtype.
     */
    public static Object inferType(String value) {
        if(value.matches("[a-zA-Z]"))
            return value;
        if(value.contains("."))
            return Double.parseDouble(value);
        return Integer.parseInt(value);
    }
}
