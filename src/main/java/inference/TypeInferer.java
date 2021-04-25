package inference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeInferer {

    /**
     * Infers the type of string.
     * <p>
     * This method works by parsing a value passed as a string,
     * creating the proper object out of it and returning it.
     * <p>
     * The limit of type inference being what they are in java,
     * user should first be aware of the type of data they are trying to
     * infer since this method only provides a basic inference mechanism
     *
     * @param value the data we want to infer.
     * @return An object representing our data realtype.
     */
    public static Object inferType(String value) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher match = pattern.matcher(value);

        if (value.equals(""))
            return "NULL";
        if (match.find())
            return value;
        if (value.contains("."))
            return Double.parseDouble(value.replace(" ", ""));
        return Integer.parseInt(value.replace(" ", ""));
    }
}
