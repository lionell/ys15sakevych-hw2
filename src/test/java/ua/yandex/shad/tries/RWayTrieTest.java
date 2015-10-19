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
        get("o").setNext(toIndex('n'), new Node());
        get("on").setNext(toIndex('e'), new Node());
        get("one").setValue(3);
        get("one").setNext(toIndex('a'), new Node());
        get("onea").setNext(toIndex('p'), new Node());
        get("oneap").setNext(toIndex('p'), new Node());
        get("oneapp").setNext(toIndex('l'), new Node());
        get("oneappl").setNext(toIndex('e'), new Node());
        get("oneapple").setValue(5);
        trie.setSize(2);
    }

    @Test
    public void testAdd_result() {
        int expectedValue = 5;

        trie.add(appleMock);
        int actualValue = get("apple").getValue();

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
    public void testDelete_hitWord_positiveResult() {
        String word = "oneapple";

        assertTrue(trie.delete(word));
    }

    @Test
    public void testDelete_missWord_negativeResult() {
        String word = "oneapp";

        assertTrue(trie.delete(word));
    }

    @Test
    public void testDelete_hitWord_deletedRedundantNodes() {
        String word = "oneapple";

        trie.delete(word);

        assertNull(get("onea"));
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

    private Node get(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            if (current == null) {
                break;
            }
            current = current.getNext(toIndex(c));
        }
        return current;
    }
}