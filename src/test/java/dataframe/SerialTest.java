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
        for (double i = 0.5; i < 5; i += 0.5)
            this.doubleSerie.add(i);

        for (int i = 0; i < 5; i++)
            assertEquals(this.doubleSerie.get(i), i + 0.5);
    }

    private void testAddOnStringSerie() {
        assertNotNull(this.stringSerie);

        for (int i = 0; i < 5; i++) {
            String intValue = String.valueOf(i);
            this.stringSerie.add("string ".concat(intValue));
            assertEquals(this.stringSerie.get(i), "string ".concat(intValue));
        }
    }

    private void testAddOnIntegerSerie() {
        assertNotNull(this.integerSerie);
        for (int i = 0; i < 5; i++) {
            this.integerSerie.add(i);
            assertEquals(this.integerSerie.get(i), i);
        }
    }

    @Test
    void remove() {
    }

    @Test
    void size() {
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