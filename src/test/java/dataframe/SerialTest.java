package dataframe;

import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SerialTest {

    private Serial<Double> doubleSerie;
    private Serial<String> stringSerie;
    private Serial<Integer> integerSerie;


    @BeforeEach
    void setUp() {
        this.doubleSerie = new Serie<>(SupportedTypes.DOUBLE);
        this.stringSerie = new Serie<>(SupportedTypes.STRING);
        this.integerSerie = new Serie<>(SupportedTypes.INTEGER);
    }


    @AfterEach
    void tearDown() {
        this.doubleSerie = null;
        this.stringSerie = null;
        this.integerSerie = null;
    }


    @Test
    void add() {
        testAddOnDoubleSerie();
        testAddOnIntegerSerie();
        testAddOnStringSerie();
    }

    private void testAddOnDoubleSerie() {
        assertNotNull(this.doubleSerie);
        for (double i = 0.5, j = 1; i < 5; i += 0.5, j++) {
            this.doubleSerie.add(i);
            assertEquals(j, this.doubleSerie.size());
        }

        for (double i = 0; i < 5; i++)
            assertEquals(this.doubleSerie.get((int) i), i / 2 + 0.5);
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
        testRemoveDoubleSerie();
        testRemoveIntegerSerie();
        testRemoveStringSerie();
    }

    private void testRemoveDoubleSerie() {
        assertNotNull(this.doubleSerie);
        this.doubleSerie.add(1.1);
        this.doubleSerie.add(1.2);

        assertThrows(IndexOutOfBoundsException.class, () -> this.doubleSerie.remove(2));
        this.doubleSerie.remove(0);
        assertEquals(1, this.doubleSerie.size());
        assertEquals(1.2, this.doubleSerie.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> this.doubleSerie.remove(1));
    }

    private void testRemoveIntegerSerie() {
        assertNotNull(this.integerSerie);
        this.integerSerie.add(1);
        this.integerSerie.add(2);

        assertThrows(IndexOutOfBoundsException.class, () -> this.integerSerie.remove(2));
        this.integerSerie.remove(0);
        assertEquals(1, this.integerSerie.size());
        assertEquals(2, this.integerSerie.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> this.integerSerie.remove(1));
    }

    private void testRemoveStringSerie() {
        assertNotNull(this.stringSerie);
        this.stringSerie.add("a");
        this.stringSerie.add("b");

        assertThrows(IndexOutOfBoundsException.class, () -> this.stringSerie.remove(2));

        this.stringSerie.remove(0);
        assertEquals(1, this.stringSerie.size());
        assertEquals("b", this.stringSerie.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> this.stringSerie.remove(1));
    }

    @Test
    void testMax() throws EmptySerieException, UnsupportedTypeException {
        assertNotNull(this.integerSerie);
        this.integerSerie.add(1);
        assertEquals(1, this.integerSerie.max());
        this.integerSerie.add(2);
        assertEquals(2, this.integerSerie.max());
        this.integerSerie.add(0);
        assertEquals(2, this.integerSerie.max());

        assertNotNull(this.doubleSerie);
        this.doubleSerie.add(1.0);
        assertEquals(1.0, this.doubleSerie.max());
        this.doubleSerie.add(2.0);
        assertEquals(2.0, this.doubleSerie.max());
        this.doubleSerie.add(0.0);
        assertEquals(2.0, this.doubleSerie.max());
    }

    @Test
    void testMaxException() {
        assertNotNull(this.stringSerie);
        this.stringSerie.add("a");
        this.stringSerie.add("c");
        this.stringSerie.add("b");

        assertThrows(UnsupportedOperationException.class, () -> this.stringSerie.max());

        assertNotNull(this.integerSerie);
        assertThrows(EmptySerieException.class, () -> this.integerSerie.max());

        assertNotNull(this.doubleSerie);
        assertThrows(EmptySerieException.class, () -> this.doubleSerie.max());
    }

    @Test
    void testMin() throws Exception {
        assertNotNull(this.integerSerie);
        this.integerSerie.add(1);
        assertEquals(1, this.integerSerie.min());
        this.integerSerie.add(2);
        assertEquals(1, this.integerSerie.min());
        this.integerSerie.add(0);
        assertEquals(0, this.integerSerie.min());

        assertNotNull(this.doubleSerie);
        this.doubleSerie.add(1.0);
        assertEquals(1.0, this.doubleSerie.min());
        this.doubleSerie.add(2.0);
        assertEquals(1.0, this.doubleSerie.min());
        this.doubleSerie.add(0.0);
        assertEquals(0.0, this.doubleSerie.min());
    }


    @Test
    void testMinException() {
        assertNotNull(this.stringSerie);
        this.stringSerie.add("a");
        this.stringSerie.add("c");
        this.stringSerie.add("b");

        assertThrows(UnsupportedOperationException.class, () -> this.stringSerie.min());

        assertNotNull(this.integerSerie);
        assertThrows(EmptySerieException.class, () -> this.integerSerie.min());

        assertNotNull(this.doubleSerie);
        assertThrows(EmptySerieException.class, () -> this.doubleSerie.min());
    }


    @Test
    void testSum() {
        assertNotNull(this.integerSerie);
        assertEquals(0, this.integerSerie.sum());
        this.integerSerie.add(1);
        assertEquals(1, this.integerSerie.sum());
        this.integerSerie.add(2);
        assertEquals(3, this.integerSerie.sum());
        this.integerSerie.add(0);
        assertEquals(3, this.integerSerie.sum());
        this.integerSerie.add(4);
        assertEquals(7, this.integerSerie.sum());
        this.integerSerie.add(11);
        assertEquals(18, this.integerSerie.sum());

        assertNotNull(this.doubleSerie);
        assertEquals(0.0, this.doubleSerie.sum());
        this.doubleSerie.add(1.0);
        assertEquals(1.0, this.doubleSerie.sum());
        this.doubleSerie.add(2.0);
        assertEquals(3.0, this.doubleSerie.sum());
        this.doubleSerie.add(0.0);
        assertEquals(3.0, this.doubleSerie.sum());
        this.doubleSerie.add(4.0);
        assertEquals(7.0, this.doubleSerie.sum());
        this.doubleSerie.add(11.0);
        assertEquals(18.0, this.doubleSerie.sum());

    }

    @Test
    void testSumException() {
        assertNotNull(this.stringSerie);
        this.stringSerie.add("a");
        this.stringSerie.add("c");
        this.stringSerie.add("b");

        assertThrows(UnsupportedOperationException.class, () -> this.stringSerie.sum());
    }

    @Test
    void size() {
        assertNotNull(this.integerSerie);
        assertEquals(0, this.integerSerie.size());

        for (int i = 0; i < 5; i++) {
            this.integerSerie.add(1);
            assertEquals(i + 1, this.integerSerie.size());
        }

        for (int i = 0; i < 5; i++) {
            this.integerSerie.remove(0);
            assertEquals(5 - (i + 1), this.integerSerie.size());
        }
    }


    @Test
    void print() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stdOut = System.out;
        System.setOut(new PrintStream(out));

        //this.integerSerie.setColumnName("Test");
        this.stringSerie.add("ab");
        this.stringSerie.add("cd");
        this.stringSerie.setColumnName("column0");
        this.stringSerie.print();
        System.setOut(stdOut);
        assertEquals("column0\nab\ncd\n", out.toString());
    }


    @Test
    void set() {
        assertNotNull(this.integerSerie);

        for (int i = 1; i <= 3; i++)
            this.integerSerie.add(i);

        // Checks that set does not modify the size
        assertEquals(3, this.integerSerie.size());
        this.integerSerie.set(2, 1);
        assertEquals(3, this.integerSerie.size());


        assertEquals(1, this.integerSerie.get(0));
        assertEquals(2, this.integerSerie.get(1));
        assertEquals(1, this.integerSerie.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> this.integerSerie.set(5, 1));
    }


    @Test
    void get() {
        assertNotNull(this.integerSerie);
        assertThrows(IndexOutOfBoundsException.class, () -> this.integerSerie.get(0));

        this.integerSerie.add(1);
        assertEquals(1, this.integerSerie.get(0));
    }

    @Test
    void mean() {
        testMeanStringSerie();
        testMeanIntegerSerie();
        testMeanDoubleSerie();
    }

    private void testMeanStringSerie() {
        assertThrows(UnsupportedTypeException.class, () -> this.stringSerie.mean());
        this.stringSerie.add("bou");
        this.stringSerie.add("ba");
        assertThrows(UnsupportedTypeException.class, () -> this.stringSerie.mean());
    }

    private void testMeanIntegerSerie() {
        assertThrows(EmptySerieException.class, () -> this.integerSerie.mean());

        this.integerSerie.add(1);
        this.integerSerie.add(1);
        this.integerSerie.add(1);
        this.integerSerie.add(1);

        assertEquals(1, this.integerSerie.mean());

        for(int i=0; i< 4; i++){
            this.integerSerie.remove(0);
        }

        //this.integerSerie.add(2);
        this.integerSerie.add(4);
        //this.integerSerie.add(-2);
        this.integerSerie.add(7);

        assertEquals((double)5.5, this.integerSerie.mean());
    }

    private void testMeanDoubleSerie(){
        assertThrows(EmptySerieException.class, () -> this.doubleSerie.mean());

        this.doubleSerie.add((double)1.1);
        this.doubleSerie.add((double)1.1);
        this.doubleSerie.add((double)1.1);

        assertEquals((double)1.1, this.doubleSerie.mean());

        for(int i=0; i< 3; i++){
            this.doubleSerie.remove(0);
        }

        this.doubleSerie.add((double)1.1);
        this.doubleSerie.add((double)10.1);
        this.doubleSerie.add((double)-6.2);

        assertEquals((double)(1.6666666666666663), this.doubleSerie.mean());


    }




}