package dataframe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerialTest {

    private Serial<Double> doubleSerie;
    private Serial<String> stringSerie;
    private Serial<Integer> integerSerie;


    @BeforeEach
    void setUp() {
        //TODO: Activate tests once Serie has been coded.
        /*
        this.doubleSerie = new Serie<Float>();
        this.stringSerie = new Serie<String>();
        this.integerSerie = new Serie<Integer>();
         */
    }


    @AfterEach
    void tearDown() {
        this.doubleSerie = null;
        this.stringSerie = null;
        this.integerSerie = null;
    }


    @Test
    void add() {
        //TODO: Activate test once add has been coded.
        //testAddOnDoubleSerie();
        //testAddOnDoubleSerie();
        //testAddOnStringSerie();
    }

    private void testAddOnDoubleSerie() {
        assertNotNull(this.doubleSerie);
        for (double i = 0.5; i < 5; i += 0.5) {
            this.doubleSerie.add(i);
            assertEquals(i + 1, this.doubleSerie.size());
        }

        for (int i = 0; i < 5; i++)
            assertEquals(this.doubleSerie.get(i), i + 0.5);
    }

    private void testAddOnStringSerie() {
        assertNotNull(this.stringSerie);

        for (int i = 0; i < 5; i++) {
            String intValue = String.valueOf(i);
            this.stringSerie.add("string ".concat(intValue));
            assertEquals(i + 1, this.stringSerie.size());
            assertEquals(this.stringSerie.get(i), "string ".concat(intValue));
        }
    }

    private void testAddOnIntegerSerie() {
        assertNotNull(this.integerSerie);
        for (int i = 0; i < 5; i++) {
            this.integerSerie.add(i);
            assertEquals(i + 1, this.integerSerie.size());
            assertEquals(this.integerSerie.get(i), i);
        }
    }


    @Test
    void remove() {
        //TODO: Activate once remove has been coded
        //testRemoveDoubleSerie();
        //testRemoveIntegerSerie();
        //testRemoveStringSerie();
    }

    private void testRemoveDoubleSerie() {
        assertNotNull(this.doubleSerie);
        this.doubleSerie.add(1.1);
        this.doubleSerie.add(1.2);

        assertThrows(NullPointerException.class, () -> this.doubleSerie.remove(2));
        this.doubleSerie.remove(0);
        assertEquals(1, this.doubleSerie.size());
        assertEquals(1.2, this.doubleSerie.get(0));

        assertThrows(NullPointerException.class, () -> this.doubleSerie.remove(1));
    }

    private void testRemoveIntegerSerie() {
        assertNotNull(this.integerSerie);
        this.integerSerie.add(1);
        this.integerSerie.add(2);

        assertThrows(NullPointerException.class, () -> this.integerSerie.remove(2));
        this.integerSerie.remove(0);
        assertEquals(1, this.integerSerie.size());
        assertEquals(2, this.integerSerie.get(0));

        assertThrows(NullPointerException.class, () -> this.integerSerie.remove(1));
    }

    private void testRemoveStringSerie() {
        assertNotNull(this.stringSerie);
        this.stringSerie.add("a");
        this.stringSerie.add("b");

        assertThrows(NullPointerException.class, () -> this.stringSerie.remove(2));

        this.stringSerie.remove(0);
        assertEquals(1, this.stringSerie.size());
        assertEquals("b", this.stringSerie.get(0));

        assertThrows(NullPointerException.class, () -> this.stringSerie.remove(1));
    }


    @Test
    void size() {
        assertNotNull(this.integerSerie);
        assertEquals(0, this.integerSerie.size());

        for(int i = 0; i < 5; i++) {
            this.integerSerie.add(1);
            assertEquals(i + 1, this.integerSerie.size());
        }

        for(int i = 0; i < 5; i++) {
            this.integerSerie.remove(0);
            assertEquals(5 - (i + 1), this.integerSerie.size());
        }
    }


    @Test
    void print() {
    }


    @Test
    void set() {
    }


    @Test
    void get() {
    }

}