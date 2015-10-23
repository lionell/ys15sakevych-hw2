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

public class RWayTrie implements Trie {

    public static final int R = 26;
    public static final char FIRST_CHAR = 'a';
    public static final int DEFAULT_VALUE = -1;

    private Node root = new Node();
    private int size;

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

    public static int toIndex(char c) {
        return c - FIRST_CHAR;
    }

    public static char toChar(int i) {
        return (char) (FIRST_CHAR + i);
    }

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

    @Override
    public boolean contains(String word) {
        Node node = get(word);
        return node != null && !node.isEmpty();
    }

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

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        StringArray queue = new StringArray();
        Node prefRoot = get(pref);
        if (prefRoot == null) {
            return queue;
        }
        queue.add(pref);
        for (String parentString : queue) {
            Node parent = get(parentString);
            for (int i = 0; i < R; ++i) {
                Node child = parent.getNext(toChar(i));
                if (child != null) {
                    String childString = parentString + toChar(i);
                    queue.add(childString);
                }
            }
        }
        StringArray words = new StringArray();
        for (String nodeString : queue) {
            if (!get(nodeString).isEmpty()) {
                words.add(nodeString);
            }
        }
        return words;
    }

    @Override
    public int size() {
        return size;
    }

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

    private void clear(String word) {
        if (word.length() > 0 && get(word).isRedundant()) {
            String subWord = word.substring(0, word.length() - 1);
            get(subWord).setNext(word.charAt(word.length() - 1), null);
            clear(subWord);
        }
    }

    Node getRoot() {
        return root;
    }

    void setSize(int newSize) {
        this.size = newSize;
    }
}
