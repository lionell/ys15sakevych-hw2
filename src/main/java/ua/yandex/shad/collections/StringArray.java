/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Ruslan Sakevych
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ua.yandex.shad.collections;

import java.util.Iterator;

public class StringArray implements Array<String> {

    /**
     * Default capacity of new StringArrays.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Storage for data.
     */
    private String[] data;

    /**
     * Actual size of array.
     */
    private int size;

    /**
     * Construct new StringArray with initial capacity.
     * @param capacity initial capacity of StringArray
     * @throws IllegalArgumentException if capacity is negative
     */
    public StringArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        data = new String[capacity];
    }

    /**
     * Construct new StringArray with default capacity.
     */
    public StringArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct new StringArray from variable length list.
     * @param values example of array
     */
    public StringArray(String... values) {
        data = values.clone();
        size = values.length;
    }

    /**
     * Returns the number of elements in array.
     * @return array size
     */
    public int size() {
        return size;
    }

    /**
     * Converts StringArray to array of Strings.
     * @return new instance of array of String with elements from StringArray
     */
    public String[] toArray() {
        String[] array = new String[size];
        System.arraycopy(data, 0, array, 0, size);
        return array;
    }

    /**
     * Returns element of StringArray on 'index' position.
     * @param index number of element in StringArray
     * @return element on 'index' position
     * @throws IndexOutOfBoundsException
     *         if index is less than 0
     *            or index greater or equals size of StringArray
     */
    public String get(int index) {
        checkBounds(index);
        return data[index];
    }

    /**
     * Set element of StringArray on 'index' position to 'value'.
     * @param index number of element in StringArray
     * @param value new value of element on 'index' position
     * @throws IndexOutOfBoundsException
     *         if index is less than 0
     *            or index greater or equals size of StringArray
     */
    public void set(int index, String value) {
        checkBounds(index);
        data[index] = value;
    }

    /**
     * Enlarge array capacity if needed.
     * @param minCapacity minimal capacity to fit
     * NOTE! element with index minCapacity is unreachable.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newSize = Math.max(data.length * 2, minCapacity);
            String[] newData = new String[newSize];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    /**
     * Adds new element to the end of array
     * @param value value of element to add
     */
    public void add(String value) {
        ensureCapacity(size + 1);
        data[size] = value;
        size++;
    }

    /**
     * Adds elements from given String[] to the end of array
     * @param values array of Strings to add
     */
    public void add(String[] values) {
        ensureCapacity(size + values.length);
        System.arraycopy(values, 0, data, size, values.length);
        size += values.length;
    }

    /**
     * Checks if array is empty.
     * @return true if there are no elements in array
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Implementation of Iterable interface.
     * @return instance of iterator
     */
    public Iterator<String> iterator() {
        return new StringArrayIterator();
    }

    /**
     * Inner iterator class.
     * @see Iterator
     */
    private class StringArrayIterator implements Iterator<String> {

        public boolean hasNext() {
            return false;
        }

        public String next() {
            return null;
        }
    }

    /**
     * Checks that index is in right range.
     * @param index the index to check
     * @throws IndexOutOfBoundsException
     *         if index is less than 0
     *            or index greater or equals size of StringArray
     */
    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Package private method used in Tests.
     * @return capacity of array
     */
    int capacity() {
        return data.length;
    }
}