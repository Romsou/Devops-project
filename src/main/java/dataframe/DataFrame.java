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
import java.util.Scanner;

public class DataFrame {

    private ArrayList<Serie> frame;
    private int nbLine;

    public DataFrame() {
        frame = new ArrayList<Serie>();
        nbLine = 0;
    }

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
            if (inferred instanceof String)
                frame.add(new Serie<>(SupportedTypes.STRING));
            else if (inferred instanceof Integer)
                frame.add(new Serie<>(SupportedTypes.INTEGER));
            else if (inferred instanceof Double)
                frame.add(new Serie<>(SupportedTypes.DOUBLE));
            i++;
        }

        do {
            i=0;
            for(String s : lineSplit) {
                SupportedTypes type = frame.get(i).type;
                frame.get(i).add(TypeInferer.inferType(s));
                i++;
            }
            nbLine++;
            line = in.nextLine();
            lineSplit = line.split(",");

            if (lineSplit.length != elementPerLine)
                throw new ColumnSizeMissmatch();

        } while(in.hasNext());
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
            addColumn(getNbColumn(), array);
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
        addColumn(getNbColumn(), array);
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

    /**
     * Create a new DataFrame with given columns index from this.
     * @param columnIndexes Array of columns index will be copy into new DataFrame
     * @return New DataFrame created
     * @throws IndexOutOfBoundsException
     */
    public DataFrame DataFrameFromColumns(ArrayList<Integer> columnIndexes) throws IndexOutOfBoundsException {
        DataFrame newFrame = new DataFrame();
        if (columnIndexes.size() == 0) {
            return newFrame;
        }

        newFrame.nbLine = this.nbLine;
        for(int i=0; i<columnIndexes.size(); i++) {
            try {
                newFrame.frame.add(i, this.frame.get(columnIndexes.get(i)));
            } catch (IndexOutOfBoundsException e) {
                throw e;
            }
        }
        return newFrame;
    }

    /**
     *
     * @param lineIndexes Array of lines index will be copy into new DataFrame
     * @return
     * @throws IndexOutOfBoundsException
     */
    public DataFrame DataFrameFromLines(ArrayList<Integer> lineIndexes) throws IndexOutOfBoundsException {
        DataFrame newFrame = new DataFrame();
        if (lineIndexes.size() == 0) {
            return  newFrame;
        }

        //First, create all columns
        for(int i = 0; i<this.getNbColumn(); i++) {
            Serie column = new Serie<>(this.getColumn(i).type);
            newFrame.frame.add(column);
        }

        //Fill column with asked lines
        for (int i=0; i<lineIndexes.size(); i++) {
            newFrame.nbLine++;
            for (int j=0; j< newFrame.getNbColumn(); j++) {
                Object OldFrameValue = null;
                try {
                    OldFrameValue = this.frame.get(j).get(lineIndexes.get(i));
                } catch (IndexOutOfBoundsException e) {
                    throw e;
                }
                newFrame.frame.get(j).add(OldFrameValue);
            }
        }
        return newFrame;
    }


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


    /**
     * Return a String it contains DataFrame line from given line number to given line number.
     *
     * @param begin First line to read
     * @param end Last line, will not be read
     * @return String contains read lines, null if begin > end
     */
    public String print(int begin, int end) {
        if (end > nbLine)
            end = nbLine;

        if (begin >= end)
            return null;

        int nbDigit = end>=1 ? (int) (Math.log10(end) + 1) : 1;
        String res = "";

        for (int i=begin; i<end; i++) {
            res += i;
            int nbSpaces = nbDigit -  (i>=1 ? (int) (Math.log10(i) + 1) : 1)+1;
            res += String.format("%1$"+nbSpaces+"s", "");

            for (int j=0; j<getNbColumn(); j++) {
                res += frame.get(j).get(i).toString() + "\t";
            }
            res += "\n";
        }
        return res;
    }

    public int getNbColumn(){
        return frame.size();
    }

    public int getNbLine(){
        return nbLine;
    }

    public Serie getColumn(int column){
        return frame.get(column);
    }

    @Override
    public String toString() {
        return print(0, nbLine);
    }

}
