package dataframe;

import CustomExceptions.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DataFrameTest {
    private ArrayList<Object> doubleSerie;
    private ArrayList<Object> stringSerie;
    private ArrayList<Object> integerSerie;
    private ArrayList<Object> booleanSerie;


    @BeforeEach
    void setUp() {
        //TODO: Activate tests once Serie has been coded.
        this.doubleSerie = new ArrayList<Object>(Collections.nCopies(10, 1.1));
        this.stringSerie = new ArrayList<Object>(Collections.nCopies(10, "a"));
        this.integerSerie = new ArrayList<Object>(Collections.nCopies(10, 1));
        this.booleanSerie = new ArrayList<Object>(Collections.nCopies(10, false));
    }


    @AfterEach
    void tearDown() {
        this.doubleSerie = null;
        this.stringSerie = null;
        this.integerSerie = null;
    }

    @Test
    void create() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        assertEquals(3, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        File f = new File("src/resources/addresses.csv");
        DataFrame frameCSV = new DataFrame(f);
        assertEquals(6, frameCSV.getNbColumn());
        assertEquals(5, frameCSV.getNbLine());

    }

    @Test
    void createException() {
        Assertions.assertThrows(UnsupportedTypeException.class, () -> {
            DataFrame frame = new DataFrame(booleanSerie);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });

        ArrayList<Object> emptyArray = new ArrayList<Object>();

        Assertions.assertThrows(EmptyArrayException.class, () -> {
            DataFrame frame = new DataFrame(emptyArray);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });


        Assertions.assertThrows(ColumnSizeMismatch.class, () -> {
            doubleSerie.add(doubleSerie.size(), 0.2);
            DataFrame frame = new DataFrame(integerSerie, doubleSerie);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });

        Assertions.assertThrows(ColumnSizeMismatch.class, () -> {
            doubleSerie.add(doubleSerie.size(), 0.2);
            DataFrame frame = new DataFrame(integerSerie);
            frame.addColumn(doubleSerie);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });

        Assertions.assertThrows(ColumnSizeMismatch.class, () -> {
            File f = new File("src/resources/addressesWrong.csv");
            DataFrame frameCSV = new DataFrame(f);
        });

    }

    @Test
    void add() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(stringSerie);
        assertEquals(1, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        frame.addColumn(1, integerSerie);
        assertEquals(2, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        assertEquals("a", frame.getColumn(0).get(0));
        frame.addColumn(doubleSerie);
        assertEquals(3, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());
        assertEquals(1.1, frame.getColumn(2).get(0));
    }

    @Test
    void remove() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(doubleSerie);
        assertEquals(1, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        frame.removeColumn(0);
        assertEquals(0, frame.getNbColumn());
        assertEquals(0, frame.getNbLine());
    }

    @Test
    void max() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(integerSerie);
        assertEquals(1, frame.max(0));

        integerSerie.add(2);
        frame.removeColumn(0);
        frame.addColumn(0, integerSerie);
        assertEquals(2, frame.max(0));

        doubleSerie.add(1.1);
        frame.addColumn(0, doubleSerie);
        assertEquals(1.1, frame.max(0));
    }

    @Test
    void maxException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.max(0);
        });
    }

    @Test
    void min() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(integerSerie);
        assertEquals(1, frame.min(0));

        integerSerie.add(0);
        frame.removeColumn(0);
        frame.addColumn(0, integerSerie);
        assertEquals(0, frame.min(0));

        doubleSerie.add(1.1);
        frame.addColumn(0, doubleSerie);
        assertEquals(1.1, frame.min(0));
    }

    @Test
    void minException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.min(0);
        });
    }

    @Test
    void sum() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(integerSerie, doubleSerie);
        assertEquals(10, frame.sum(0));
        assertEquals(11.0, (double) frame.sum(1), 1e-10);
    }

    @Test
    void sumException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.sum(0);
        });
    }

    @Test
    void print() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException, URISyntaxException, ColumnSizeMismatch {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        assertEquals("0 a\t1\t1.1\t\n", frame.print(0, 1));
        assertEquals("0 a\t1\t1.1\t\n1 a\t1\t1.1\t\n2 a\t1\t1.1\t\n", frame.print(0, 3));
        assertEquals("3 a\t1\t1.1\t\n4 a\t1\t1.1\t\n", frame.print(3, 5));
        assertEquals("8 a\t1\t1.1\t\n9 a\t1\t1.1\t\n", frame.print(8, 100));
        assertNull(frame.print(0, 0));
        assertNull(frame.print(9, 8));

        URL resource = getClass().getClassLoader().getResource("addresses.csv");

        File f = new File(resource.toURI());
        DataFrame frameCSV = new DataFrame(f);
        assertEquals("0 John\tDoe\t120 jefferson st.\tRiverside\t NJ\t8075\t\n", frameCSV.print(0, 1));
        assertEquals("2 John Da Man\tRepici\t120 Jefferson St.\tRiverside\t NJ\t8075\t\n" +
                "3 Stephen\tTyler\t7452 Terrace At the Plaza road\tSomeTown\tSD\t91234\t\n" +
                "4 NULL\tBlankman\tNULL\tSomeTown\t SD\t298\t\n", frameCSV.print(2, 5));
        assertNull(frameCSV.print(0, 0));
        assertNull(frameCSV.print(1, 0));

    }

    @Test
    void selectFromColumn() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException, EmptySerieException, ColumnNotFoundException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);

        ArrayList<String> columnsNames = new ArrayList<>();
        DataFrame frame1 = frame.DataFrameFromColumns(columnsNames);
        assertEquals(0, frame1.getNbColumn());
        assertEquals(0, frame1.getNbLine());

        columnsNames.add("column1");

        DataFrame frame2 = frame.DataFrameFromColumns(columnsNames);
        assertEquals(1, frame2.getNbColumn());
        assertEquals(10, frame2.getNbLine());
        assertEquals(1, frame2.max(0));

        columnsNames.remove(0);
        columnsNames.add("column0");
        columnsNames.add("column2");
        DataFrame frame3 = frame.DataFrameFromColumns(columnsNames);

        assertEquals(2, frame3.getNbColumn());
        assertEquals(10, frame3.getNbLine());
        assertEquals(1.1, frame3.max(1));
    }

    @Test
    void selectFromColumnException() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("column1");
        columnNames.add("column" + frame.getNbLine());
        Assertions.assertThrows(ColumnNotFoundException.class, () -> {
            DataFrame frame2 = frame.DataFrameFromColumns(columnNames);
        });
    }

    @Test
    void selectFromLine() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException, EmptySerieException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);

        ArrayList<Integer> lineIndexes = new ArrayList<>();
        DataFrame frame1 = frame.DataFrameFromLines(lineIndexes);
        assertEquals(0, frame1.getNbColumn());
        assertEquals(0, frame1.getNbLine());

        lineIndexes.add(1);

        DataFrame frame2 = frame.DataFrameFromLines(lineIndexes);
        assertEquals(3, frame2.getNbColumn());
        assertEquals(1, frame2.getNbLine());
        assertEquals(1, frame2.max(1));
        assertEquals(1.1, frame2.max(2));

        lineIndexes.remove(0);
        lineIndexes.add(0);
        lineIndexes.add(2);
        lineIndexes.add(8);
        DataFrame frame3 = frame.DataFrameFromLines(lineIndexes);

        assertEquals(3, frame3.getNbColumn());
        assertEquals(3, frame3.getNbLine());
        assertEquals(1, frame3.max(1));
        assertEquals(1.1, frame3.max(2));

    }

    @Test
    void selectFromLineException() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        ArrayList<Integer> lineIndexes = new ArrayList<>();
        lineIndexes.add(1);
        lineIndexes.add(frame.getNbLine());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            DataFrame frame2 = frame.DataFrameFromLines(lineIndexes);
        });
    }

    @Test
    void selectFromMinValue() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException, ColumnNotFoundException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);

        DataFrame newFrame = frame.DataFrameFromMinValue("column0", 0);
        assertNull(newFrame);

        newFrame = frame.DataFrameFromMinValue("column1", 0);
        assertEquals(10, newFrame.getNbLine());
        assertEquals(3, newFrame.getNbColumn());

        newFrame = frame.DataFrameFromMinValue("column1", 2);
        assertEquals(0, newFrame.getNbLine());
        assertEquals(3, newFrame.getNbColumn());

        newFrame = frame.DataFrameFromMinValue("column2", 1.0);
        assertEquals(10, newFrame.getNbLine());
        assertEquals(3, newFrame.getNbColumn());

        newFrame = frame.DataFrameFromMinValue("column2", 1.2);
        assertEquals(0, newFrame.getNbLine());
        assertEquals(3, newFrame.getNbColumn());
    }

    @Test
    void selectFromMinValueException() throws ColumnSizeMismatch, EmptyArrayException, UnsupportedTypeException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        Assertions.assertThrows(ColumnNotFoundException.class, () -> {
            DataFrame frame2 = frame.DataFrameFromMinValue("aaa", 1);
        });
    }

    @Test
    void printException() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            DataFrame frame = new DataFrame(stringSerie);
            frame.sum(0);
        });
    }

}