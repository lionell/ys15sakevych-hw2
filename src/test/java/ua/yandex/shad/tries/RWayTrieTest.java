package ua.yandex.shad.tries;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ua.yandex.shad.tries.RWayTrie.Node;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {

    private static final char FIRST_CHAR = 'a';

    @Mock private Tuple oneMock;
    @Mock private Tuple appleMock;
    @Mock private Tuple oneDriveMock;
    @Mock private Tuple oneAppleMock;

    private final RWayTrie trie = new RWayTrie();
    private final Node root = trie.getRoot();

    @Before
    public void setup() {
        when(oneMock.getTerm()).thenReturn("one");
        when(oneMock.getWeight()).thenReturn(3);
        when(appleMock.getTerm()).thenReturn("apple");
        when(appleMock.getWeight()).thenReturn(5);
        when(oneDriveMock.getTerm()).thenReturn("onedrive");
        when(oneDriveMock.getWeight()).thenReturn(8);
        when(oneAppleMock.getTerm()).thenReturn("oneapple");
        when(oneAppleMock.getWeight()).thenReturn(8);

        root.setNext(toIndex('o'), new Node());
        root.getNext(toIndex('o'))
                .setNext(toIndex('n'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .setNext(toIndex('e'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .setValue(3);
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .setNext(toIndex('a'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .getNext(toIndex('a'))
                .setNext(toIndex('p'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .getNext(toIndex('a'))
                .getNext(toIndex('p'))
                .setNext(toIndex('p'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .getNext(toIndex('a'))
                .getNext(toIndex('p'))
                .getNext(toIndex('p'))
                .setNext(toIndex('l'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .getNext(toIndex('a'))
                .getNext(toIndex('p'))
                .getNext(toIndex('p'))
                .getNext(toIndex('l'))
                .setNext(toIndex('e'), new Node());
        root.getNext(toIndex('o'))
                .getNext(toIndex('n'))
                .getNext(toIndex('e'))
                .getNext(toIndex('a'))
                .getNext(toIndex('p'))
                .getNext(toIndex('p'))
                .getNext(toIndex('l'))
                .getNext(toIndex('e'))
                .setValue(5);
        trie.setSize(2);
    }

    @Test
    public void testAdd_result() {
        int expectedValue = 5;

        trie.add(appleMock);
        int actualValue =
                root.getNext(toIndex('a'))
                        .getNext(toIndex('p'))
                        .getNext(toIndex('p'))
                        .getNext(toIndex('l'))
                        .getNext(toIndex('e'))
                        .getValue();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testContains_hitWord_positiveResult() {
        String word = "one";

        assertTrue(trie.contains(word));
    }

    @Test
    public void testContains_missWord_negativeResult() {
        String word = "two";

        assertFalse(trie.contains(word));
    }

    @Test
    public void testSize_result() {
        int expectedSize = 2;

        int actualSize = trie.size();

        assertEquals(expectedSize, actualSize);
    }

    private int toIndex(char c) {
        return c - FIRST_CHAR;
    }

    private int toChar(int i) {
        return FIRST_CHAR + i;
    }
}