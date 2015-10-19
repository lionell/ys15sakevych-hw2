package ua.yandex.shad.tries;

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

    @Override
    public void add(Tuple t) {
        String key = t.getTerm();
        int value = t.getWeight();
        Node current = root;
        for (char c : key.toCharArray()) {
            if (current.getNext(c) == null) {
                current.setNext(c, new Node());
            }
            current = current.getNext(c);
        }
        if (current.isEmpty()) {
            current.setValue(value);
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
        return true;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        return size;
    }

    private Node get(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            if (current.getNext(c) == null) {
                current = null;
                break;
            }
            current = current.getNext(c);
        }
        return current;
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
