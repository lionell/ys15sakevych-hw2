package ua.yandex.shad.collections;

import java.util.Iterator;

public interface Array<T> extends Iterable<T> {

    int size();

    T[] toArray();

    T get(int index);

    void set(int index, T value);

    void ensureCapacity(int minCapacity);

    void add(T value);

    void add(T[] values);

    boolean isEmpty();

    Iterator<T> iterator();
}
