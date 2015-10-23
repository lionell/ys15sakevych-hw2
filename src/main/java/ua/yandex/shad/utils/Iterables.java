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

package ua.yandex.shad.utils;

import java.util.Iterator;

/**
 * Class is used to compare two instances of classes that implements Iterable
 * interface.
 * @param <T> type of objects in Iterable
 */
public class Iterables<T> {
    public String toString(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        if (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
        }
        while (iterator.hasNext()) {
            stringBuilder.append(" ");
            stringBuilder.append(iterator.next().toString());
        }
        return stringBuilder.toString();
    }
}
