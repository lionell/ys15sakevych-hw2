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

public class LimitDecorator implements Iterable<String> {

    private final Iterable<String> generator;
    private final int limiter;

    public LimitDecorator(Iterable<String> generator, int limiter) {
        this.generator = generator;
        this.limiter = limiter;
    }

    public Iterator<String> iterator() {
        return new LimitedIterator();
    }
    private class LimitedIterator implements Iterator<String> {

        private Iterator<String> iterator = generator.iterator();
        private String next;
        private int left = limiter;

        {
            if (iterator.hasNext()) {
                next = iterator.next();
                left--;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null && left >= 0;
        }

        @Override
        public String next() {
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
