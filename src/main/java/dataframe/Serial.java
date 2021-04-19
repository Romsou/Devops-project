package dataframe;

public interface Serial<T> {

    /**
     * Adds an element to the serie at the last position
     *
     * @param e the element to add
     * @return true if the addition succeeded
     */
    boolean add(T e);

    /**
     * Removes the element at the given index
     *
     * @param index the index of the element to be removed
     * @return true if the removal succeeded
     */
    boolean remove(int index);

    /**
     * Gets the size of the serie
     *
     * @return the number of elements in the serie
     */
    int size();

    /**
     * Creates a String to represent the serie
     *
     * @return the String who represents the serie
     */
    String toString();

    /**
     * Prints on standard output a representation of the serie
     */
    void print();

    /**
     * Modify the element of the serie at the given index
     *
     * @param e     the new element
     * @param index the position of the new element
     * @return true if the modification is successful
     */
    boolean set(T e, int index);

    /**
     * Gets the element at the specified index
     *
     * @param index a valid position
     * @return the element at the specified index
     */
    T get(int index);


}
