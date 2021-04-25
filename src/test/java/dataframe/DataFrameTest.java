package dataframe;

import CustomExceptions.ColumnSizeMissmatch;
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
    void create() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException, ColumnSizeMissmatch {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        assertEquals(3, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        File f = new File("src/resources/addresses.csv");
        DataFrame frameCSV = new DataFrame(f);
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


        Assertions.assertThrows(ColumnSizeMissmatch.class, () -> {
            doubleSerie.add(doubleSerie.size(),0.2);
            DataFrame frame = new DataFrame(integerSerie, doubleSerie);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });

        Assertions.assertThrows(ColumnSizeMissmatch.class, () -> {
            doubleSerie.add(doubleSerie.size(),0.2);
            DataFrame frame = new DataFrame(integerSerie);
            frame.addColumn(doubleSerie);
            assertEquals(0, frame.getNbColumn());
            assertEquals(10, frame.getNbLine());
        });

        Assertions.assertThrows(ColumnSizeMissmatch.class, () -> {
            File f = new File("src/resources/addressesWrong.csv");
            DataFrame frameCSV = new DataFrame(f);
        });

    }

    @Test
    void add() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
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
    void remove() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
        DataFrame frame = new DataFrame(doubleSerie);
        assertEquals(1, frame.getNbColumn());
        assertEquals(10, frame.getNbLine());

        frame.removeColumn(0);
        assertEquals(0, frame.getNbColumn());
        assertEquals(0, frame.getNbLine());
    }

    @Test
    void max() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException, ColumnSizeMissmatch {
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
    void min() throws UnsupportedTypeException, EmptyArrayException, EmptySerieException, ColumnSizeMissmatch {
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
    void sum() throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
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
    void print() throws UnsupportedTypeException, EmptyArrayException, FileNotFoundException, URISyntaxException, ColumnSizeMissmatch {
        // TODO: Find a way to redirect stdout to the inside of the program

        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        System.out.println(frame.print(0,frame.getNbLine())); //Entire DataFrame
        System.out.println(frame.print(0,1000)); //Entire DataFrame
        System.out.println(frame.print(0,frame.getNbLine()/2)); //first half DataFrame
        System.out.println(frame.print(frame.getNbLine()/2,frame.getNbLine())); //last half DataFrame
        System.out.println(frame.print(0,1)); //one line
        System.out.println(frame.print(0,0)); //zero line

        URL resource = getClass().getClassLoader().getResource("addresses.csv");

        File f = new File(resource.toURI());
        DataFrame frameCSV = new DataFrame(f);
        System.out.println(frameCSV.toString());
        System.out.println(frameCSV.print(0,frameCSV.getNbLine())); //Entire DataFrame
        System.out.println(frameCSV.print(0,1000)); //Entire DataFrame
        System.out.println(frameCSV.print(0,frameCSV.getNbLine()/2)); //first half DataFrame
        System.out.println(frameCSV.print(frameCSV.getNbLine()/2,frameCSV.getNbLine())); //last half DataFrame
        System.out.println(frameCSV.print(0,1)); //one line
        System.out.println(frameCSV.print(0,0)); //zero line
    }

    @Test
    void selectFromColumn() throws ColumnSizeMissmatch, EmptyArrayException, UnsupportedTypeException, EmptySerieException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);

        ArrayList<Integer> columnsIndexes = new ArrayList<>();
        DataFrame frame1 = frame.DataFrameFromColumns(columnsIndexes);
        assertEquals(0, frame1.getNbColumn());
        assertEquals(0, frame1.getNbLine());

        columnsIndexes.add(1);

        DataFrame frame2 = frame.DataFrameFromColumns(columnsIndexes);
        assertEquals(1, frame2.getNbColumn());
        assertEquals(10, frame2.getNbLine());
        assertEquals(1, frame2.max(0));

        columnsIndexes.remove(0);
        columnsIndexes.add(0);
        columnsIndexes.add(2);
        DataFrame frame3 = frame.DataFrameFromColumns(columnsIndexes);

        assertEquals(2, frame3.getNbColumn());
        assertEquals(10, frame3.getNbLine());
        assertEquals(1.1, frame3.max(1));
    }

    @Test
    void selectFromColumnException() throws ColumnSizeMissmatch, EmptyArrayException, UnsupportedTypeException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        ArrayList<Integer> columnsIndex = new ArrayList<>();
        columnsIndex.add(1);
        columnsIndex.add(frame.getNbLine());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            DataFrame frame2 = frame.DataFrameFromColumns(columnsIndex);
        });
    }

    @Test
    void selectFromLine() throws ColumnSizeMissmatch, EmptyArrayException, UnsupportedTypeException, EmptySerieException {
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
    void selectFromLineException() throws ColumnSizeMissmatch, EmptyArrayException, UnsupportedTypeException {
        DataFrame frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        ArrayList<Integer> lineIndexes = new ArrayList<>();
        lineIndexes.add(1);
        lineIndexes.add(frame.getNbLine());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            DataFrame frame2 = frame.DataFrameFromLines(lineIndexes);
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