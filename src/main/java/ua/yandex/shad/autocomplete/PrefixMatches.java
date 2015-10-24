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

package ua.yandex.shad.autocomplete;

import ua.yandex.shad.utils.LimitDecorator;
import ua.yandex.shad.tries.RWayTrie;
import ua.yandex.shad.collections.Tuple;
import ua.yandex.shad.tries.Trie;

public class PrefixMatches {

    /**
     * Lower bound of word length.
     */
    private static final int MIN_WORD_LENGTH = 3;

    /**
     * Default value for parameter K in wordsWithPrefix method.
     */
    private static final int DEFAULT_K = 3;

    /**
     * Actual Trie to store data.
     */
    private Trie trie = new RWayTrie();

    /**
     * Loads words from {@code strings} to Trie.
     * If string form {@code strings} contains more than on word then
     * it will be split by whitespaces.
     * @param strings input strings
     * @return amount of words added
     */
    public int load(String... strings) {
        for (String string : strings) {
            for (String str : string.split("\\s+")) {
                if (str.length() >= MIN_WORD_LENGTH) {
                    trie.add(new Tuple(str, str.length()));
                }
            }
        }
        return size();
    }

    /**
     * Checks if {@code word} is in memory.
     * @param word actual word to check
     * @return true,  if word is in memory
     *         false, otherwise
     */
    public boolean contains(String word) {
        return trie.contains(word);
    }

    /**
     * Deletes {@code word} form memory.
     * @param word actual word to delete
     * @return true,  if success
     *         false, otherwise
     */
    public boolean delete(String word) {
        return trie.delete(word);
    }

    /**
     * Gets words that matches {@code pref}.
     * @param pref actual prefix ot match.
     * @return instance of Iterable class with words
     * @see Iterable
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEFAULT_K);
    }

    /**
     * Gets words that matches {@code pref} and limit different lengths
     * with parameter {@code k}.
     * @param pref actual prefix to match
     * @param k number of different lengths
     * @return instance of Iterable class with words
     * @see Iterable
     */
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < MIN_WORD_LENGTH) {
            throw new IllegalArgumentException();
        }
        return new LimitDecorator(trie.wordsWithPrefix(pref), k);
    }

    /**
     * Gets amount of words in memory.
     * @return count of words
     */
    public int size() {
        return trie.size();
    }
}
