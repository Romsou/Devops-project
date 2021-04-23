package dataframe;

import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.lang.Class;

public class Serie<T> implements Serial<T> {

    private List<T> elements;

    public Serie() {
        elements = new ArrayList<>();
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

    public void print() {
        for (T elt : elements)
            System.out.println(elt);
    }

    public String toString() {
        return "" + elements;
    }


}