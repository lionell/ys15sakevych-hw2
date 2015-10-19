package ua.yandex.shad.tries;

public class RWayTrie implements Trie {

    private static final int R = 26;
    private static final char FIRST_CHAR = 'a';

    private Node root = new Node();
    private int size;

    static class Node {

        private int value;
        private Node[] next = new Node[R];

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        Node getNext(int i) {
            return next[i];
        }

        void setNext(int i, Node x) {
            next[i] = x;
        }
    }

    @Override
    public void add(Tuple t) {
        String key = t.getTerm();
        int value = t.getWeight();
        Node current = root;
        for (char c : key.toCharArray()) {
            int i = toIndex(c);
            if (current.next[i] == null) {
                current.next[i] = new Node();
            }
            current = current.next[i];
        }
        if (current.value == 0) {
            current.value = value;
            size++;
        }
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<String> words() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    private int toIndex(char c) {
        return c - FIRST_CHAR;
    }

    private int toChar(int i) {
        return FIRST_CHAR + i;
    }

    private Node get(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            int i = toIndex(c);
            if (current.next[i] == null) {
                current = null;
                break;
            }
            current = current.next[i];
        }
        return current;
    }

    Node root() {
        return root;
    }

}
