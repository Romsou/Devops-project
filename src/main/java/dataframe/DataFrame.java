package dataframe;

import CustomExceptions.*;
import inference.TypeInferer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFrame {

    private final ArrayList<Serie> frame;
    private int nbLine;


    public DataFrame() {
        frame = new ArrayList<Serie>();
        nbLine = 0;
    }


    /**
     * Create a DataFrame from a CSV file. Note that we not yet handle CSV File with escape
     * character " (quotation mark), and then we not handle CSV file which contain , (comma) in cells.
     * Note that columns name will be "column"+<Column-Index> by default.
     *
     * @param csv CSV file to read.
     * @throws FileNotFoundException If file doesn't exist with specify path
     * @throws ColumnSizeMismatch    if CSV file contains different column length
     */
    public DataFrame(File csv) throws FileNotFoundException, ColumnSizeMismatch {
        frame = new ArrayList<Serie>();
        nbLine = 0;

        FileReader fileR = new FileReader(csv);
        Scanner in = new Scanner(fileR);

        //Guess type from first line
        String line = in.nextLine();
        String[] lineSplit = line.split(",");
        int elementPerLine = lineSplit.length;
        int i = 0;
        for (String s : lineSplit) {
            Object inferred = TypeInferer.inferType(s);

            if (inferred instanceof String)
                frame.add(new Serie<>(SupportedTypes.STRING, "column" + i));
            else if (inferred instanceof Integer)
                frame.add(new Serie<>(SupportedTypes.INTEGER, "column" + i));
            else if (inferred instanceof Double)
                frame.add(new Serie<>(SupportedTypes.DOUBLE, "column" + i));
            i++;
        }

        do {
            i = 0;
            for (String s : lineSplit) {
                frame.get(i).add(TypeInferer.inferType(s));
                i++;
            }
            nbLine++;
            line = in.nextLine();
            lineSplit = line.split(",");

            if (lineSplit.length != elementPerLine)
                throw new ColumnSizeMismatch();

        } while (in.hasNext());
    }


    /**
     * Create a new DataFrame and fill its column with given arrays. Note that columns name will be
     * "column"+<Column-Index> by default.
     *
     * @param arrays columns to add in DataFrame.
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public DataFrame(ArrayList<Object>... arrays) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
        frame = new ArrayList<Serie>();
        nbLine = 0;

        for (ArrayList<Object> array : arrays)
            addColumn(getNbColumn(), array);
    }


    /**
     * Add a given array at given index into DataFrame.
     *
     * @param columnIndex Index where array should be add.
     * @param array       Array of Objects
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public void addColumn(int columnIndex, ArrayList<Object> array) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
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
                column = new Serie<>(SupportedTypes.STRING, "column" + columnIndex);
            else if (objType instanceof Integer)
                column = new Serie<>(SupportedTypes.INTEGER, "column" + columnIndex);
            else if (objType instanceof Double)
                column = new Serie<>(SupportedTypes.DOUBLE, "column" + columnIndex);
            else
                throw new UnsupportedTypeException();

            //Fill Serie
            for (Object o : array)
                column.add(o);

            frame.add(columnIndex, column);
        } else if (array.size() == 0)
            throw new EmptyArrayException();
        else if (nbLine != array.size())
            throw new ColumnSizeMismatch();
    }


    /**
     * Add column at the end of DataFrame
     *
     * @param array Array to add in DataFrame
     * @throws UnsupportedTypeException
     * @throws EmptyArrayException
     * @throws ColumnSizeMismatch
     */
    public void addColumn(ArrayList<Object> array) throws UnsupportedTypeException, EmptyArrayException, ColumnSizeMismatch {
        addColumn(getNbColumn(), array);
    }


    /**
     * Removes column specify by it index
     *
     * @param column column index to remove
     * @throws IndexOutOfBoundsException
     */
    public void removeColumn(int column) throws IndexOutOfBoundsException {
        frame.remove(column);
        if (frame.isEmpty())
            nbLine = 0;
    }


    /**
     * Create a new DataFrame from this with given columns names.
     *
     * @param columnNames Array of columns index will be copy into new DataFrame
     * @return New DataFrame created
     * @throws IndexOutOfBoundsException
     */
    public DataFrame DataFrameFromColumns(ArrayList<String> columnNames) throws ColumnNotFoundException {
        DataFrame newFrame = new DataFrame();
        if (columnNames.size() == 0) {
            return newFrame;
        }

        newFrame.nbLine = this.nbLine;
        for (int i = 0; i < columnNames.size(); i++)
            try {
                newFrame.frame.add(this.findColumnByName(columnNames.get(i)));
            } catch (ColumnNotFoundException e) {
                throw e;
            }
        return newFrame;
    }


    /**
     * Create a new DataFrame from this with given lines indexes.
     *
     * @param lineIndexes Array of lines index will be copy into new DataFrame
     * @return New DataFrame created
     * @throws IndexOutOfBoundsException
     */
    public DataFrame DataFrameFromLines(ArrayList<Integer> lineIndexes) throws IndexOutOfBoundsException {
        DataFrame newFrame = new DataFrame();
        if (lineIndexes.size() == 0)
            return newFrame;

        //First, create all columns
        for (int i = 0; i < this.getNbColumn(); i++)
            newFrame.frame.add(new Serie<>(this.getColumn(i).type));

        //Fill column with asked lines
        for (int i = 0; i < lineIndexes.size(); i++) {
            newFrame.nbLine++;
            for (int j = 0; j < newFrame.getNbColumn(); j++)
                try {
                    Object OldFrameValue = this.frame.get(j).get(lineIndexes.get(i));
                    newFrame.frame.get(j).add(OldFrameValue);
                } catch (IndexOutOfBoundsException e) {
                    throw e;
                }
        }
        return newFrame;
    }

    /**
     * Create a new DataFrame by selecting each line which contains in a specific column, a minimum given double value.
     *
     * @param columnName Column name to check
     * @param minValue Minimum value
     * @return New DataFrame created, null if column type is not double
     * @throws IndexOutOfBoundsException
     * @throws ColumnNotFoundException
     */
    public DataFrame DataFrameFromMinValue(String columnName, double minValue) throws ColumnNotFoundException {
        DataFrame newFrame = new DataFrame();
        Serie serie = findColumnByName(columnName);

        if (!serie.getType().equals(SupportedTypes.DOUBLE) && !serie.getType().equals(SupportedTypes.INTEGER))
            return null;

        //First, create all columns
        for (int i = 0; i < this.getNbColumn(); i++)
            newFrame.frame.add(new Serie<>(this.getColumn(i).type));

        //Fill column with asked lines
        for (int i = 0; i < this.getNbLine(); i++) {
            boolean addLine = false;
            if (serie.getType().equals(SupportedTypes.DOUBLE)) {
                if ((Double) serie.get(i) >= minValue)
                    addLine = true;
            } else if (serie.getType().equals(SupportedTypes.INTEGER)) {
                if (Double.valueOf((Integer)serie.get(i)) >= minValue)
                    addLine = true;
            }
            if (addLine) {
                newFrame.nbLine++;
                for (int j = 0; j < newFrame.getNbColumn(); j++) {
                    Object OldFrameValue = null;
                    OldFrameValue = this.frame.get(j).get(i);
                    newFrame.frame.get(j).add(OldFrameValue);
                }
            }
        }
        return newFrame;
    }

    /**
     * Create a new DataFrame by selecting each line which contains in a specific column, a minimum given int value.
     *
     * @param columnName Column name to check
     * @param minValue Minimum value
     * @return New DataFrame created, null if column type is not int
     * @throws IndexOutOfBoundsException
     * @throws ColumnNotFoundException
     */
    public DataFrame DataFrameFromMinValue(String columnName, int minValue) throws IndexOutOfBoundsException, ColumnNotFoundException {
        return DataFrameFromMinValue(columnName, Double.valueOf(minValue));
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
     * @param end   Last line, will not be read
     * @return String contains read lines, null if begin > end
     */
    public String print(int begin, int end) {
        if (end > nbLine)
            end = nbLine;

        if (begin >= end)
            return null;

        int nbDigit = end >= 1 ? (int) (Math.log10(end)) : 1;
        String res = "";

        for (int i = begin; i < end; i++) {
            res += i;
            int nbSpaces = nbDigit - (i >= 1 ? (int) (Math.log10(i) + 1) : 1) + 1;
            nbSpaces = nbSpaces == 0 ? 1 : nbSpaces;

            res += String.format("%1$" + nbSpaces + "s", "");

            for (int j = 0; j < getNbColumn(); j++)
                res += frame.get(j).get(i).toString() + "\t";

            res += "\n";
        }
        return res;
    }


    /**
     * Search the serie with matching name in all DataFrame
     *
     * @param name Name of serie to search
     * @return Matching Serie if found, null otherwise
     * @throws ColumnNotFoundException
     */
    public Serie findColumnByName(String name) throws ColumnNotFoundException {
        for (Serie s : frame)
            if (s.getColumnName().equals(name.toLowerCase()))
                return s;
        throw new ColumnNotFoundException();
    }


    /**
     * Get the number of columns
     *
     * @return number of columns
     */
    public int getNbColumn() {
        return frame.size();
    }


    /**
     * Get the number of lines
     *
     * @return number of lines
     */
    public int getNbLine() {
        return nbLine;
    }


    /**
     * Get a specific column from is index.
     *
     * @param column column index
     * @return matching serie
     */
    public Serie getColumn(int column) {
        return frame.get(column);
    }


    @Override
    public String toString() {
        return print(0, nbLine);
    }

}
