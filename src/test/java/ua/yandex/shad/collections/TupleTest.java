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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lionell on 10/24/15.
 */
public class TupleTest {

    //<editor-fold desc="Tests for Tuple(String term, int weight)">
    @Test
    public void testTuple_constructor_membersShouldBeSet() {
        String term = "yeah";
        int weight = 13;
        String expectedTerm = "yeah";
        int expectedWeight = 13;

        Tuple tuple = new Tuple(term, weight);
        String actualTerm = tuple.getTerm();
        int actualWeight = tuple.getWeight();

        assertEquals(expectedTerm, actualTerm);
        assertEquals(expectedWeight, actualWeight);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for equals(Object o)">
    @Test
    public void testEquals_nullReference_negativeResult() {
        String term = "one";
        int weight = 12;

        Tuple tuple = new Tuple(term, weight);

        assertNotEquals(tuple, null);
    }

    @Test
    public void testEquals_differentClasses_negativeResult() {
        String term = "one";
        int weight = 12;

        Tuple tuple = new Tuple(term, weight);

        assertNotEquals(tuple, new Object());
    }

    @Test
    public void testEquals_equalReferences_positiveResult() {
        String term = "one";
        int weight = 12;

        Tuple tuple = new Tuple(term, weight);

        assertEquals(tuple, tuple);
    }

    @Test
    public void testEquals_equalTuples_positiveResult() {
        String term = "one";
        int weight = 3;

        Tuple one = new Tuple(term, weight);
        Tuple two = new Tuple(term, weight);

        assertEquals(one, two);
    }

    @Test
    public void testEquals_equalTuplesWithNullTerms_positiveResult() {
        int weight1 = 2;
        int weight2 = 2;

        Tuple one = new Tuple(null, weight1);
        Tuple two = new Tuple(null, weight2);

        assertEquals(one, two);
    }

    @Test
    public void testEquals_differentTuplesOneWithNullTerm_negativeResult() {
        String term = "one";
        int weight1 = 2;
        int weight2 = 2;

        Tuple one = new Tuple(term, weight1);
        Tuple two = new Tuple(null, weight2);

        assertNotEquals(one, two);
    }

    @Test
    public void testEquals_differentTuples_negativeResult() {
        String term1 = "one";
        int weight1 = 2;
        String term2 = "two";
        int weight2 = 3;

        Tuple one = new Tuple(term1, weight1);
        Tuple two = new Tuple(term2, weight2);

        assertNotEquals(one, two);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for hashCode()">
    @Test
    public void testHashCode_equalTuples_equalHashes() {
        String term = "one";
        int weight = 3;
        int expectedHashCode = new Tuple("one", 3).hashCode();

        Tuple tuple = new Tuple(term, weight);
        int actualHashCode = tuple.hashCode();

        assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void testHashCode_differentTuplesOneWithNullTerm_negativeResult() {
        int weight = 3;
        int expectedHashCode = new Tuple("one", 3).hashCode();

        Tuple tuple = new Tuple(null, weight);
        int actualHashCode = tuple.hashCode();

        assertNotEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void testHashCode_differentTuples_differentHashes() {
        String term = "two";
        int weight = 3;
        int expectedHashCode = new Tuple("one", 2).hashCode();

        Tuple tuple = new Tuple(term, weight);
        int actualHashCode = tuple.hashCode();

        assertNotEquals(expectedHashCode, actualHashCode);
    }
    //</editor-fold>
}
