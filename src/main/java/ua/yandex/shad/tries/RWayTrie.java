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

import ua.yandex.shad.collections.StringArray;
import ua.yandex.shad.collections.Tuple;

import java.util.Iterator;

public class RWayTrie implements Trie {

    /**
     * Amount of links per node.
     */
    public static final int R = 26;

    /**
     * First character in alphabet is used.
     */
    public static final char FIRST_CHAR = 'a';

    /**
     * Value that represents that Node don't contains anything.
     */
    public static final int DEFAULT_VALUE = -1;

    /**
     * Root of the Trie.
     */
    private Node root = new Node();

    /**
     * Amount of words stored in trie.
     */
    private int size;

    /**
     * Nested class to represent Node of Trie.
     */
    static class Node {

        private int value = DEFAULT_VALUE;
        private Node[] next = new Node[R];

        public int getValue() {
            return value;
        }

        public void setValue(int newValue) {
            this.value = newValue;
        }

        public Node getNext(char c) {
            return next[toIndex(c)];
        }

        public void setNext(char c, Node x) {
            next[toIndex(c)] = x;
        }

        public boolean isEmpty() {
            return value == DEFAULT_VALUE;
        }

        public boolean isRedundant() {
            for (int i = 0; i < R; ++i) {
                if (next[i] != null) {
                    return false;
                }
            }
            return isEmpty();
        }
    }

    /**
     * Converts character {@code c} to index in link array.
     * @param c character to convert
     * @return desired index
     */
    private static int toIndex(char c) {
        return c - FIRST_CHAR;
    }

    /**
     * Converts index {@code i} in link array to a character.
     * @param i actual index in array
     * @return desired character
     */
    private static char toChar(int i) {
        return (char) (FIRST_CHAR + i);
    }

    /**
     * Adds tuple to Trie.
     * @param t tuple to add
     */
    @Override
    public void add(Tuple t) {
        String key = t.getTerm();
        int value = t.getWeight();
        Node cur = root;
        for (char c : key.toCharArray()) {
            if (cur.getNext(c) == null) {
                cur.setNext(c, new Node());
            }
            cur = cur.getNext(c);
        }
        if (cur.isEmpty()) {
            cur.setValue(value);
            size++;
        }
    }

    /**
     * Checks if Trie contains {@code word}.
     * @param word actual word to check
     * @return true,  if yes
     *         false, otherwise
     */
    @Override
    public boolean contains(String word) {
        Node node = get(word);
        return node != null && !node.isEmpty();
    }

    /**
     * Deletes word from Trie.
     * @param word actual word to delete
     * @return true,  if success
     *         false, otherwise
     */
    @Override
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        }
        get(word).setValue(DEFAULT_VALUE);
        clear(word);
        size--;
        return true;
    }

    /**
     * Gets <b>all</b> words from Trie.
     * @return instance of Iterable class with desired words
     * @see Iterable
     */
    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    /**
     * Gets <b>only</b> words that match prefix {@code pref}.
     * @param pref actual prefix to match
     * @return instance of Iterable class with desired words
     */
    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        return new WordsWithPrefixIterable(pref);
    }

    private class WordsWithPrefixIterable implements Iterable<String> {
        private String pref;

        public WordsWithPrefixIterable(String pref) {
            this.pref = pref;
        }

        @Override
        public Iterator<String> iterator() {
            return new WordsWithPrefixIterator(pref);
        }

        private class WordsWithPrefixIterator implements Iterator<String> {
            private String next;
            private Iterator<String> queueIterator;
            private StringArray queue = new StringArray();

            private void updateNext() {
                next = null;
                while (queueIterator.hasNext()) {
                    next = queueIterator.next();
                    enqueuePrefix(next);
                    if (!get(next).isEmpty()) {
                        break;
                    }
                }
                if (next != null && get(next).isEmpty()) {
                    next = null;
                }
            }

            private void enqueuePrefix(String pref) {
                Node parent = get(pref);
                for (int i = 0; i < R; ++i) {
                    Node child = parent.getNext(toChar(i));
                    if (child != null) {
                        String childString = pref + toChar(i);
                        queue.add(childString);
                    }
                }
            }

            public WordsWithPrefixIterator(String pref) {
                Node prefRoot = get(pref);
                if (prefRoot != null) {
                    queueIterator = queue.iterator();
                    queue.add(pref);
                    updateNext();
                }
            }

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public String next() {
                String current = next;
                updateNext();
                return current;
            }
        }
    }

    /**
     * Gets amount of Trie.
     * @return count of words in Trie
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Additional method to find Node in Trie with {@code key}.
     * @param key string to match
     * @return desired Node of Trie
     */
    private Node get(String key) {
        Node cur = root;
        for (char c : key.toCharArray()) {
            if (cur.getNext(c) == null) {
                return null;
            }
            cur = cur.getNext(c);
        }
        return cur;
    }

    /**
     * Additional method to clear Trie from redundant nodes.
     * It's recursively try to delete Node that has no links and contains
     * no information inside.
     * @param word string to start from
     */
    private void clear(String word) {
        if (word.length() > 0 && get(word).isRedundant()) {
            String subWord = word.substring(0, word.length() - 1);
            get(subWord).setNext(word.charAt(word.length() - 1), null);
            clear(subWord);
        }
    }

    /**
     * Gets root node of Trie.
     * Needed for testing.
     * @return root of Trie
     */
    Node getRoot() {
        return root;
    }

    /**
     * Setter for size member.
     * Needed for testing.
     * @param newSize value to set
     */
    void setSize(int newSize) {
        this.size = newSize;
    }
}
