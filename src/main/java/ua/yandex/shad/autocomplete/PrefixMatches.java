package ua.yandex.shad.autocomplete;

import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

public class PrefixMatches {

    private Trie trie;

    public int load(String... strings) {
        for (String string : strings) {
            for (String str : string.split("\\s+")) {
                trie.add(new Tuple(str, str.length()));
            }
        }
        return size();
    }

    public boolean contains(String word) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean delete(String word) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        return trie.size();
    }
}
