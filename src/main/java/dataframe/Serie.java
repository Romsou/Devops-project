package dataframe;

import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;

import java.util.ArrayList;
import java.util.List;

public class Serie<T> implements Serial<T> {

    private final List<T> elements;

    protected SupportedTypes type;

    private String columnName;

    public Serie(SupportedTypes type) {
        this.elements = new ArrayList<>();
        this.type = type;
        columnName = null;
    }

    public Serie(SupportedTypes type, String name) {
        this.elements = new ArrayList<>();
        this.type = type;
        this.columnName = name;
    }

    public void add(T e) {
        elements.add(e);
    }


    public void remove(int index) {
        elements.remove(index);
    }


    public T get(int index) {
        return elements.get(index);
    }


    public void set(int index, T e) {
        elements.set(index, e);
    }


    public int size() {
        return elements.size();
    }

    public T min() throws UnsupportedTypeException, EmptySerieException {
        switch (type) {
            case DOUBLE:
                return (T) minDouble();
            case INTEGER:
                return (T) minInteger();
            case STRING:
                return minString();
            default:
                throw new UnsupportedTypeException();
        }
    }

    private Object minDouble() throws EmptySerieException {
        if (elements.size() == 0)
            throw new EmptySerieException();

        double min = (double) elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            double current_element = (double) elements.get(i);
            min = Math.min(min, current_element);
        }

        return min;
    }

    private Object minInteger() throws EmptySerieException {
        if (elements.size() == 0)
            throw new EmptySerieException();

        int min = (int) elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            int current_element = (int) elements.get(i);
            min = Math.min(min, current_element);
        }

        return min;
    }

    private T minString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get the minimum of a string");
    }

    public T max() throws UnsupportedTypeException, EmptySerieException {
        switch (type) {
            case DOUBLE:
                return (T) maxDouble();
            case INTEGER:
                return (T) maxInteger();
            case STRING:
                return maxString();
            default:
                throw new UnsupportedTypeException();
        }
    }

    public double mean() throws UnsupportedTypeException, EmptySerieException {
        if(type == SupportedTypes.STRING)
            throw new UnsupportedTypeException();

        if(size() == 0)
            throw new EmptySerieException();


        double sum = this.sum() instanceof Integer ? ((Integer) this.sum()).doubleValue() : (double) this.sum();
        return sum / size();
    }

    private Object maxDouble() throws EmptySerieException {
        if (elements.size() == 0)
            throw new EmptySerieException();

        double max = (double) elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            double current_element = (double) elements.get(i);
            max = Math.max(max, current_element);
        }

        return max;
    }

    private Object maxInteger() throws EmptySerieException {
        if (elements.size() == 0)
            throw new EmptySerieException();

        int max = (int) elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            int current_element = (int) elements.get(i);
            max = Math.max(max, current_element);
        }

        return max;
    }

    private T maxString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't get the maximum of a string");
    }

    @Override
    public T sum() throws UnsupportedOperationException {
        switch (type) {
            case DOUBLE:
                return (T) sumDouble();
            case INTEGER:
                return (T) sumInteger();
            case STRING:
                return sumString();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private Object sumDouble() {
        double sum = 0;

        if (elements.size() == 0)
            return 0.0;

        for (T elt : elements)
            sum += (Double) elt;

        return sum;
    }

    private Object sumInteger() {
        Integer sum = 0;

        if (elements.size() == 0)
            return 0;

        for (T elt : elements)
            sum += (Integer) elt;

        return sum;
    }

    private T sumString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't sum strings");
    }


    public void print() {
        System.out.println(columnName);
        for (T elt : elements)
            System.out.println(elt);
    }

    public SupportedTypes getType() {
        return type;
    }


    public void setType(SupportedTypes type) {
        this.type = type;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName.toLowerCase();
    }

    public String toString() {
        return "" + elements;
    }
}