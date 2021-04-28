
import java.util.ArrayList;
import java.util.Collections;

import CustomExceptions.ColumnNotFoundException;
import CustomExceptions.ColumnSizeMismatch;
import CustomExceptions.EmptyArrayException;
import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;
import dataframe.*;

public class Main {
    public static void main(String[] args)
            throws ColumnNotFoundException, UnsupportedTypeException, EmptyArrayException, EmptySerieException, ColumnSizeMismatch {

        ArrayList<Object> doubleSerie = new ArrayList<Object>(Collections.nCopies(8, 1.1));
        doubleSerie.add(0.8);
        doubleSerie.add(5.2);
        ArrayList<Object> stringSerie = new ArrayList<Object>(Collections.nCopies(10, "a"));
        ArrayList<Object> integerSerie = new ArrayList<Object>(Collections.nCopies(10, 1));

        DataFrame frame;
        System.out.println("Creation of a Dataframe");
        frame = new DataFrame(stringSerie, integerSerie, doubleSerie);
        System.out.println(frame.print(0, frame.getNbLine()));

        //Print a specific column
        ArrayList<String> columnsNames = new ArrayList<>();
        System.out.println("Selection of the first column:");
        columnsNames.add("column0");
        DataFrame frame2 = frame.DataFrameFromColumns(columnsNames);
        System.out.println(frame2.print(0, frame2.getNbLine()));

        ArrayList<String> columnsNames2 = new ArrayList<>();
        System.out.println("Selection of the two last columns:");
        columnsNames2.add("column1");
        columnsNames2.add("column2");
        DataFrame frame3 = frame.DataFrameFromColumns(columnsNames2);
        System.out.println(frame3.print(0, frame3.getNbLine()));

        //Print a specific line
        System.out.println("First line of the Dataframe:");
        ArrayList<Integer> lineIndexes = new ArrayList<>();
        lineIndexes.add(1);
        frame2 = frame.DataFrameFromLines(lineIndexes);
        System.out.println(frame2.print(0, frame2.getNbColumn()));

        //Sum of a column
        System.out.println("Sum of the second column : " + frame.sum(1));

        //Min and Max of a column
        System.out.println("Minimum of the third column : " + frame.min(2));
        System.out.println("Maximum of the third column : " + frame.max(2));

        //Adding a column to the Dataframe
        System.out.println("Adding a column to the Dataframe:");
        ArrayList<Object> newSerie = new ArrayList<Object>(Collections.nCopies(10,5));
        frame.addColumn(newSerie);
        System.out.println(frame.print(0,frame.getNbColumn()));

        //Deleting a column from the Dataframe
        System.out.println("Delete the first column of the Dataframe");
        frame.removeColumn(0);
        System.out.println(frame.print(0,frame.getNbColumn()));
    }
}
