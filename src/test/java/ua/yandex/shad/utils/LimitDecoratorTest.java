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

import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
/**
 * Created by lionell on 10/24/15.
 */
public class LimitDecoratorTest {

    //<editor-fold desc="Set up tests">
    private final Iterable<String> empty = new Iterable<String>() {
        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public String next() {
                    return null;
                }
            };
        }
    };

    private final Iterable<String> tic = new Iterable<String>() {
        String[] words = {"tic"};

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < 1;
                }

                @Override
                public String next() {
                    return words[index++];
                }
            };
        }
    };

    private final Iterable<String> ticTacToe = new Iterable<String>() {
        String[] words = {"tic", "tac", "tictac", "tictactoe"};

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < 3;
                }

                @Override
                public String next() {
                    return words[index++];
                }
            };
        }
    };
    //</editor-fold>

    //<editor-fold desc="Tests for iterator()">
    @Test
    public void testIterator_emptyGenerator_negativeHasNext() {
        int k = 3;

        LimitDecorator decorator = new LimitDecorator(empty, k);
        Iterator<String> iterator = decorator.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator_emptyGenerator_nextShouldThrowException() {
        int k = 3;

        LimitDecorator decorator = new LimitDecorator(empty, k);
        Iterator<String> iterator = decorator.iterator();

        iterator.next();
    }

    @Test
    public void testIterator_singleWordGenerator_result() {
        int k = 3;
        String expectedWord = "tic";

        LimitDecorator decorator = new LimitDecorator(tic, k);
        Iterator<String> iterator = decorator.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(expectedWord, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator_limiterIsLessThanGeneratorSize_result() {
        int k = 2;
        String expectedWord1 = "tic";
        String expectedWord2 = "tac";
        String expectedWord3 = "tictac";

        LimitDecorator decorator = new LimitDecorator(ticTacToe, k);
        Iterator<String> iterator = decorator.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(expectedWord1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(expectedWord2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(expectedWord3, iterator.next());
        assertFalse(iterator.hasNext());
    }
    //</editor-fold>
}
