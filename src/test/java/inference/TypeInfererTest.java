package inference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeInfererTest {

    @Test
    void inferType() {
        String string = "Test";
        String alphanum = "1Test";
        String alphareal = "1.0Test";

        String integer = "1234567890";
        String real ="1.234567890";


        Object inferred = TypeInferer.inferType(string);
        assertTrue(inferred instanceof String);

        inferred = TypeInferer.inferType(alphanum);
        assertTrue(inferred instanceof String);

        inferred = TypeInferer.inferType(alphareal);
        assertTrue(inferred instanceof String);

        inferred = TypeInferer.inferType(integer);
        assertTrue(inferred instanceof Integer);

        inferred = TypeInferer.inferType(real);
        assertTrue(inferred instanceof Double);
    }
}