package dataframe;

public interface Frame<T> {

    T sum(int index);

    T min(int index);

    T max(int index);
}
