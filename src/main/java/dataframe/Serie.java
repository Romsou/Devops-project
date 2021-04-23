package dataframe;

import java.util.List;
import java.util.ArrayList;

public class Serie<T> implements Serial<T> {

    protected List<T> elements;
    protected  SupportedTypes type;


    public Serie(SupportedTypes type) {
        elements = new ArrayList<>();
        this.type = type;
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

    @Override
    public T sum() throws UnsupportedOperationException{
        switch(type) {
            case DOUBLE:
                return (T) sumDouble();
            case INTEGER:
                return (T) sumInteger();
            case STRING:
                return sumString();
            default:
                throw new IllegalArgumentException();
        }
    }

    private Object sumDouble() {
        double sum = 0;

        if(elements.size() == 0)
            return 0.0;

        for(T elt: elements)
            sum += (Double) elt;

        return sum;
    }

    private Object sumInteger() {
        double sum = 0;

        if(elements.size() == 0)
            return 0;

        for(T elt: elements)
            sum += (Integer) elt;

        return sum;
    }

    private T sumString() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't sum strings");
    }

    public void print() {
        for (T elt : elements)
            System.out.println(elt);
    }


    public String toString() {
        return "" + elements;
    }

}