
public interface Serial<T>{
	
	/**
	 * A methode to add a element to the serie at last position
	 * @param e the element to add
	 * @return true if the addition is effective
	 */
	boolean add(T e);

	/**
	 * Remove the element at indicated position
	 * @param index the index of element to be removed
	 * @return true if the removal is effective
	 */
	boolean remove(int index);

	/**
	 * Get the size of the serie 
	 * @return the number of elements in the serie
	 */
	int size();

	/**
	 * Create a String to represent the serie
	 * @return the String who represents the serie
	 */
	String toString();

	/**
	 * Print on standard output a representation of 
	 * the serie
	 */
	void print();

	/**
	 * Modify one element of the serie
	 * @param e the new element
	 * @param index the index position of new element
	 * @return true if the modification is successful
	 */
	boolean set(T e, int index);

	/**
	 * Get the element at the specified index
	 * @param index a valid position
	 * @return the element at the specified index
	 */
	T get(int index);


}