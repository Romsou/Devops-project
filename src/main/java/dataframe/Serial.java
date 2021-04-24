package dataframe;

import CustomExceptions.EmptySerieException;
import CustomExceptions.UnsupportedTypeException;

public interface Serial<T> {

    /**
     * Adds an element to the serie at the last position
     *
     * @param e the element to add
     */
    void add(T e);

    /**
     * Removes the element at the given index
     *
     * @param index the index of the element to be removed
     */
    void remove(int index);

    /**
     * Gets the element at the specified index
     *
     * @param index a valid position
     * @return the element at the specified index
     */
    T get(int index);

    /**
     * Modify the element of the serie at the given index
     *
     * @param e     the new element
     * @param index the position of the new element
     */
    void set(int index, T e);

    /**
     * Gets the size of the serie
     *
     * @return the number of elements in the serie
     */
    int size();

    T min() throws Exception;

    T max() throws UnsupportedTypeException, EmptySerieException;

    /**
     * Computes the sum of all the elements contained within this serie.
     * <p>
     * If this serie contains no element, it returns 0.0. Else
     *
     * @return
     */
    T sum();

    /**
     * Prints on standard output a representation of the serie
     */
    void print();

    /**
     * Creates a String to represent the serie
     *
     * @return the String who represents the serie
     */
    String toString();

}
