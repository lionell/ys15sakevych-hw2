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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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

public interface Array<T> extends Iterable<T> {

    /**
     * Get size of array.
     * @return actual size of array
     */
    int size();

    /**
     * Convert array to Java array.
     * @return instance of Java array with elements
     */
    T[] toArray();

    /**
     * Get element at {@code index} position.
     * @param index position of element in array
     * @return element on {@code position}
     */
    T get(int index);

    /**
     * Set element at {@code index} position to {@code value}.
     * @param index position of element
     * @param value new value for element
     */
    void set(int index, T value);

    /**
     * Resize internal array to fit {@code minCapacity}.
     * @param minCapacity minimal capacity to fit
     */
    void ensureCapacity(int minCapacity);

    /**
     * Add new element to array.
     * {@code value} element will be placed at the end of array.
     * @param value element to place
     */
    void add(T value);

    /**
     * Add several elements to array.
     * All of them will be placed at the end of array.
     * Order of elements in array will be the same as in {@code values} array.
     * @param values elements to place
     */
    void add(T[] values);

    /**
     * Check if array is empty.
     * @return true,  if array is empty
     *         false, otherwise
     */
    boolean isEmpty();

    /**
     * Get iterator, to move through array.
     * @return instance of iterator
     * @see Iterator
     */
    Iterator<T> iterator();
}
