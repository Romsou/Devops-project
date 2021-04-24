package dataframe;

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

    public DataFrame(File csv) throws FileNotFoundException {
        frame = new ArrayList<Serie>();

        FileReader fileR = new FileReader(csv);
        Scanner in = new Scanner(fileR);

        //Guess type from first line
        String line = in.nextLine();
        String[] lineSplit = line.split(",");
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
        } while(in.hasNext());

    }

    /**
     * Create a new DataFrame and fill its column with given arrays.
     *
     * @param arrays columns to add in DataFrame.
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public DataFrame(ArrayList<Object>... arrays) throws UnsupportedTypeException, EmptyArrayException {
        frame = new ArrayList<Serie>();

        for(ArrayList<Object> array : arrays)
            addColumn(frame.size(), array);
    }

    /**
     * Add a given array at given index to DataFrame.
     * @param columnIndex Index where array should be add.
     * @param array Array of Objects
     * @throws UnsupportedTypeException if array elements type is not Integer, Double or String
     */
    public void addColumn(int columnIndex, ArrayList<Object> array) throws UnsupportedTypeException, EmptyArrayException {
        if (array.size() > 0) {
            Object objType = array.get(0);
            Serie column = null;
            if (objType instanceof String) {
                column = new Serie<>(SupportedTypes.STRING);
            } else if (objType instanceof Integer) {
                column = new Serie<>(SupportedTypes.INTEGER);
            } else if (objType instanceof Double) {
                column = new Serie<>(SupportedTypes.DOUBLE);
            } else {
                throw new UnsupportedTypeException();
            }

            //Fill Serie
            for (Object o : array) {
                column.add(o);
            }
            frame.add(columnIndex, column);
        } else {
            throw new EmptyArrayException();
        }
    }

    public void removeColumn(int column) {
        frame.remove(column);
    }

   /* //What should we add? TODO
    public void add(int column) {
        // To implement
    }

    //What should we remove? TODO
    public void remove(int column, int row) {
        // To implement
        //Should we simply remove? or put null in given box?
    }

    //What should we delete? TODO
    public void delete(int column, int row) {
        // To implement
        //Should we simply remove? or put null in given box?
    }*/

    public Object sum(int column) throws UnsupportedOperationException{
        return frame.get(column).sum();
    }

    public Object min(int column) throws EmptySerieException, UnsupportedTypeException {
        return frame.get(column).min();
    }

    public Object max(int column) throws EmptySerieException, UnsupportedTypeException {
        return frame.get(column).max();
    }

    public int getColumnSize(){
        return frame.size();
    }

    public int getLineSize(){
        if(frame.size() == 0)
            return 0;
        return frame.get(0).size();
    }

    public Serie getColumn(int column){
        return frame.get(column);
    }

    @Override
    public String toString() {
        String res = "";
        for (Serie serie : frame) {
            res += serie.toString() + "\n";
        }
        return res;
    }
}
