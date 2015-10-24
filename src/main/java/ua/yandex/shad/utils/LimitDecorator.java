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

package ua.yandex.shad.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LimitDecorator implements Iterable<String> {

    /**
     * Generator to decorate.
     */
    private final Iterable<String> generator;

    /**
     * Amount of lengths of words to match.
     */
    private final int limiter;

    /**
     * Constructor is used to set generator and limiter members.
     * @param generator actual generator to use
     * @param limiter actual value of limiter to set
     */
    public LimitDecorator(Iterable<String> generator, int limiter) {
        this.generator = generator;
        this.limiter = limiter;
    }

    /**
     * Gets iterator to go through decorated generator.
     * @return desired iterator
     * @see Iterator
     */
    public Iterator<String> iterator() {
        return new LimitedIterator();
    }

    /**
     * Nested class that implements Iterator interface.
     * Used to create instance in #iterator method.
     */
    private class LimitedIterator implements Iterator<String> {

        /**
         * Inner iterator to iterate on generator.
         */
        private Iterator<String> iterator = generator.iterator();

        /**
         * Actual next value to return.
         * Can be <b>null</b>.
         */
        private String next;

        /**
         * Stores number of lengths left.
         */
        private int left = limiter;

        {
            if (iterator.hasNext()) {
                next = iterator.next();
                left--;
            }
        }

        /**
         * Checks if there elements left in generator and checks upper bound of
         * length to fit.
         * @return true,  if at least one more element left
         *         false, otherwise
         */
        @Override
        public boolean hasNext() {
            return next != null && left >= 0;
        }

        /**
         * Gets next element(if has).
         * @return desired element
         * @throws NoSuchElementException, if there are no element available.
         */
        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String current = next;
            if (iterator.hasNext()) {
                next = iterator.next();
                if (next.length() != current.length()) {
                    left--;
                }
            } else {
                next = null;
            }
            return current;
        }
    }
}
