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

package ua.yandex.shad.tries;

public class Tuple {

    /**
     * Word stored in tuple.
     */
    private final String term;

    /**
     * Weight of the word stored in tuple.
     */
    private final int weight;

    /**
     * Constructor of tuple.
     * @param term word to store
     * @param weight weight to store
     */
    public Tuple(String term, int weight) {
        this.term = term;
        this.weight = weight;
    }

    /**
     * Getter method for {@code term} member.
     * @return actual word stored in tuple
     */
    public String getTerm() {
        return term;
    }

    /**
     * Getter method for {@code weight} member.
     * @return actual weight stored in tuple
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Default Intellij IDEA implementation of equals method.
     * @param o object to test on equality
     * @return true,  if objects are equal
     *         false, otherwise
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tuple tuple = (Tuple) o;

        if (weight != tuple.weight) {
            return false;
        }
        if (term != null) {
            return term.equals(tuple.term);
        } else {
            return tuple.term == null;
        }
    }

    /**
     * Default Intellij IDEA implementation of hashCode method.
     * @return hash of data stored in pair
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 0;
        if (term != null) {
            result = term.hashCode();
        }
        result = PRIME * result + weight;
        return result;
    }
}
