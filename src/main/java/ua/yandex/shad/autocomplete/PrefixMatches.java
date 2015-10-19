package ua.yandex.shad.autocomplete;

import ua.yandex.shad.collections.LimitedGeneratorDecorator;
import ua.yandex.shad.tries.Tuple;
import ua.yandex.shad.tries.Trie;

public class PrefixMatches {

    private static final int MIN_WORD_LENGTH = 3;
    private Trie trie;

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

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < MIN_WORD_LENGTH) {
            throw new IllegalArgumentException();
        }
        return new LimitedGeneratorDecorator(trie.wordsWithPrefix(pref), k);
    }

    public int size() {
        return trie.size();
    }
}
