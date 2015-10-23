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

public interface Trie {

    /**
     * Adds word from {@code tuple} to trie.
     * @param tuple word and it's weight to add
     */
    void add(Tuple tuple);

    /**
     * Checks if trie contains a {@code word}.
     * @param word actual word to check
     * @return true,  if contains
     *         false, if not
     */
    boolean contains(String word);

    /**
     * Deletes {@code word} from trie.
     * @param word actual word to delete
     * @return true,  if success
     *         false, if not
     */
    boolean delete(String word);

    /**
     * Gets <b>all</b> words from trie.
     * @return instance of Iterable interface
     * @see Iterable
     */
    Iterable<String> words();

    /**
     * Gets words from trie, that matches {@code pref}.
     * @param pref actual prefix to match
     * @return instance of Iterable interface
     * @see Iterable
     */
    Iterable<String> wordsWithPrefix(String pref);

    /**
     * Gets amount of words in trie.
     * @return actual size
     */
    int size();
}
