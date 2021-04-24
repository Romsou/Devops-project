package dataframe;

import CustomExceptions.EmptyArrayException;
import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {
    private ArrayList<Object> doubleSerie;
    private ArrayList<Object> stringSerie;
    private ArrayList<Object> integerSerie;
    private ArrayList<Object> booleanSerie;


    @BeforeEach
    void setUp() {
        //TODO: Activate tests once Serie has been coded.
        this.doubleSerie = new ArrayList<Object>(Collections.nCopies(10,1.1));
        this.stringSerie = new ArrayList<Object>(Collections.nCopies(10,"a"));
        this.integerSerie = new ArrayList<Object>(Collections.nCopies(10,1));
        this.booleanSerie = new ArrayList<Object>(Collections.nCopies(10, false));

    }


    @AfterEach
    void tearDown() {
        this.doubleSerie = null;
        this.stringSerie = null;
        this.integerSerie = null;
    }

    @Test
    void create() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        assertEquals(3, frame.getColumnSize());
        assertEquals(10, frame.getLineSize());

        File f = new File("src/resources/addresses.csv");
        DataFrame frameCSV = new DataFrame(f);
    }

    @Test
    void createException() {
        Assertions.assertThrows(UnsupportedTypeException.class, () -> {
            DataFrame frame = new DataFrame(booleanSerie);
            assertEquals(0, frame.getColumnSize());
            assertEquals(10, frame.getLineSize());
        });

        ArrayList<Object> emptyArray = new ArrayList<Object>();

        Assertions.assertThrows(EmptyArrayException.class, () -> {
            DataFrame frame = new DataFrame(emptyArray);
            assertEquals(0, frame.getColumnSize());
            assertEquals(10, frame.getLineSize());
        });
    }

    @Test
    void add() throws UnsupportedTypeException, EmptyArrayException {
        DataFrame frame = new DataFrame(stringSerie);
        assertEquals(1, frame.getColumnSize());
        assertEquals(10, frame.getLineSize());

        frame.addColumn(1, integerSerie);
        assertEquals(2, frame.getColumnSize());
        assertEquals(10, frame.getLineSize());

        assertEquals("a", frame.getColumn(0).get(0));
        frame.addColumn(0, doubleSerie);
        assertEquals(3, frame.getColumnSize());
        assertEquals(10, frame.getLineSize());
        assertEquals(1.1, frame.getColumn(0).get(0));
    }

    @Test
    void remove() throws UnsupportedTypeException, EmptyArrayException {
        DataFrame frame = new DataFrame(doubleSerie);
        assertEquals(1, frame.getColumnSize());
        assertEquals(10, frame.getLineSize());

        frame.removeColumn(0);
        assertEquals(0, frame.getColumnSize());
        assertEquals(0, frame.getLineSize());
    }

    @Test
    void max() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException {
        DataFrame frame = new DataFrame(integerSerie, doubleSerie, stringSerie);
        assertEquals(1, frame.max(0));

        integerSerie.add(2);
        frame.removeColumn(0);
        frame.addColumn(0, integerSerie);
        assertEquals(2, frame.max(0));

        assertEquals(1.1, frame.max(1));
    }

    @Test
    void maxException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.max(0);
        });
    }

    @Test
    void min() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException {
        DataFrame frame = new DataFrame(integerSerie, doubleSerie);
        assertEquals(1, frame.min(0));
        assertEquals(1.1, frame.min(1));

        integerSerie.add(0);
        frame.removeColumn(0);
        frame.addColumn(0, integerSerie);
        assertEquals(0, frame.min(0));
    }

    @Test
    void minException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.min(0);
        });
    }

    @Test
    void sum() throws UnsupportedTypeException, EmptyArrayException {
        DataFrame frame = new DataFrame(integerSerie, doubleSerie);
        assertEquals(10, frame.sum(0));
        assertEquals(11.0, (double)frame.sum(1), 1e-10);
    }

    @Test
    void sumException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.sum(0);
        });
    }

    @Test
    void print() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException, URISyntaxException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        System.out.println(frame.toString());

        URL resource = getClass().getClassLoader().getResource("addresses.csv");

        File f = new File(resource.toURI());
        DataFrame frameCSV = new DataFrame(f);
        System.out.println(frameCSV.toString());

    }

}