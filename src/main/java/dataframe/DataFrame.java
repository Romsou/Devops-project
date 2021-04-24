package dataframe;

import CustomExceptions.ColumnSizeMissmatch;
import CustomExceptions.EmptyArrayException;
import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;
import inference.TypeInferer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DataFrame {

    private ArrayList<Serie> frame;
    private int nbLine;

    /**
     * Create a DataFrame from a CSV file. Note that we not yet handle CSV File with escape
     * character " (quotation mark), and then we not handle CSV file which contain , (comma) in cells.
     *
     * @param csv CSV file to read.
     * @throws FileNotFoundException If file doesn't exist with specify path
     * @throws ColumnSizeMissmatch if CSV file contains different column length
     */
    public DataFrame(File csv) throws FileNotFoundException, ColumnSizeMissmatch {
        frame = new ArrayList<Serie>();
        nbLine = 0;

        FileReader fileR = new FileReader(csv);
        Scanner in = new Scanner(fileR);

        //Guess type from first line
        String line = in.nextLine();
        String[] lineSplit = line.split(",");
        int elementPerLine = lineSplit.length;
        int i = 0;
        for(String s : lineSplit) {
            Object inferred = TypeInferer.inferType(s);
            if (inferred instanceof String) {
                frame.add(new Serie<>(SupportedTypes.STRING));
            } else if (inferred instanceof Integer) {
                frame.add(new Serie<>(SupportedTypes.INTEGER));
            } else if (inferred instanceof Double) {
                frame.add(new Serie<>(SupportedTypes.DOUBLE));
            }
            i++;
        }

        do {
            i=0;
            for(String s : lineSplit) {
                SupportedTypes type = frame.get(i).type;
                frame.get(i).add(TypeInferer.inferType(s));
                i++;
            }
            line = in.nextLine();
            lineSplit = line.split(",");
            if (lineSplit.length != elementPerLine) {
                throw new ColumnSizeMissmatch();
            }
        } while(in.hasNext());
         nbLine = i-1;

    }

    /**
     * Create a new DataFrame and fill its column with given arrays.
     *
     * @param arrays columns to add in DataFrame.
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public DataFrame(ArrayList<Object>... arrays) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
        frame = new ArrayList<Serie>();
        nbLine = 0;

        for(ArrayList<Object> array : arrays)
            addColumn(frame.size(), array);
    }

    /**
     * Add a given array at given index into DataFrame.
     *
     * @param columnIndex Index where array should be add.
     * @param array Array of Objects
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public void addColumn(int columnIndex, ArrayList<Object> array) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
        try {
            frame.remove(columnIndex);
        } catch (IndexOutOfBoundsException e) {

        }
        if (array.size() > 0 && nbLine == array.size() || array.size() > 0 && nbLine == 0) {
            if (nbLine == 0)
                nbLine = array.size();

            Object objType = array.get(0);
            Serie column = null;
            if (objType instanceof String)
                column = new Serie<>(SupportedTypes.STRING);
            else if (objType instanceof Integer)
                column = new Serie<>(SupportedTypes.INTEGER);
            else if (objType instanceof Double)
                column = new Serie<>(SupportedTypes.DOUBLE);
            else
                throw new UnsupportedTypeException();

            //Fill Serie
            for (Object o : array)
                column.add(o);

            frame.add(columnIndex, column);
        }
        else if (array.size() == 0)
            throw new EmptyArrayException();
        else  if (nbLine != array.size())
            throw new ColumnSizeMissmatch();
    }

    /**
     * Add column at the end of DataFrame
     *
     * @param array Array to add in DataFrame
     * @throws UnsupportedTypeException
     * @throws EmptyArrayException
     * @throws ColumnSizeMissmatch
     */
    public void addColumn(ArrayList<Object> array) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMissmatch {
        addColumn(frame.size(), array);
    }

    /**
     * Removes column specify by it index
     *
     * @param column column index to remove
     * @throws IndexOutOfBoundsException
     */
    public void removeColumn(int column) throws IndexOutOfBoundsException{
        frame.remove(column);
        if (frame.isEmpty())
            nbLine = 0;
    }

    /*//What should we delete? TODO
    public void delete(int column, int row) {
        // To implement
        //Should we simply remove? or put null in given box?
    }*/

    /**
     * Computes column elements sum. Allow only if column contains Integer or Double
     *
     * @param column index of column to compute
     * @return Column elements sum, 0 if column is empty
     * @throws UnsupportedOperationException
     */
    public Object sum(int column) throws UnsupportedOperationException {
        return frame.get(column).sum();
    }

    /**
     * Finds column minimum value
     *
     * @param column index of column
     * @return Column minimum value found, error if column is empty
     * @throws EmptySerieException
     * @throws UnsupportedTypeException
     */
    public Object min(int column) throws EmptySerieException, UnsupportedTypeException {
        return frame.get(column).min();
    }

    /**
     * Finds column maximum value
     *
     * @param column index of column
     * @return Column maximum value found, throws error if column is empty
     * @throws EmptySerieException
     * @throws UnsupportedTypeException
     */
    public Object max(int column) throws EmptySerieException, UnsupportedTypeException {
        return frame.get(column).max();
    }

    public int getColumnSize(){
        return frame.size();
    }

    public int getLineSize(){
        return nbLine;
    }

    public Serie getColumn(int column){
        return frame.get(column);
    }

    @Override
    public String toString() {
        String res = "";
        int nbDigit = getLineSize()>=1 ? (int) (Math.log10(getLineSize()) + 1) : 1;

        for (int i=0; i<getLineSize(); i++) {
            res += i;
            int nbSpaces = nbDigit -  (i>=1 ? (int) (Math.log10(i) + 1) : 1)+1;
            res += String.format("%1$"+nbSpaces+"s", "");

            for (int j=0; j<frame.size(); j++) {
                res += frame.get(j).get(i).toString() + "\t";
            }
            res += "\n";
        }
        return res;
    }
}
