package ua.yandex.shad.tries;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ua.yandex.shad.tries.RWayTrie.Node;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.collections.Iterables;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {

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

        root.setNext('o', new Node());
        get("o").setValue(1);
        get("o").setNext('n', new Node());
        get("on").setValue(2);
        get("on").setNext('e', new Node());
        get("one").setValue(3);
        get("one").setNext('a', new Node());
        get("onea").setNext('p', new Node());
        get("oneap").setNext('p', new Node());
        get("oneapp").setNext('l', new Node());
        get("oneappl").setNext('e', new Node());
        get("oneapple").setValue(5);
        trie.setSize(4);
    }

    @Test
    public void testAdd_result() {
        int expectedValue = 5;

        trie.add(appleMock);
        int actualValue = get("apple").getValue();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testAdd_existingWord_sizeDoNotChanged() {
        int expectedSize = 4;

        trie.add(oneMock);
        int actualSize = trie.size();

        assertEquals(expectedSize, actualSize);
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

        assertFalse(trie.delete(word));
    }

    @Test
    public void testDelete_hitWord_deletedRedundantNodes() {
        String word = "oneapple";

        trie.delete(word);

        assertNull(get("onea"));
    }

    @Test
    public void testDelete_hitWord_nodesDoNotChanged() {
        String word = "one";

        trie.delete(word);

        assertNotNull(get("oneapple"));
    }

    @Test
    public void testDelete_emptyString_nodesDoNotChanged() {
        String word = "";

        trie.delete(word);

        assertNotNull(get("oneapple"));
    }

    @Test
    public void testWords_result() {
        String expectedResult = "o on one oneapple";

        String actualResult = new Iterables<String>().toString(trie.words());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_hitsSeveralWords_result() {
        String pref = "one";
        String expectedResult = "one oneapple";

        String actualResult = new Iterables<String>().toString(trie.wordsWithPrefix(pref));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_hitsOneWord_result() {
        String pref = "oneapp";
        String expectedResult = "oneapple";

        String actualResult = new Iterables<String>().toString(trie.wordsWithPrefix(pref));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_doNotHitAnyWord_negativeResultIteratorHasNext() {
        String pref = "two";

        assertFalse(trie.wordsWithPrefix(pref).iterator().hasNext());
    }

    @Test
    public void testSize_result() {
        int expectedSize = 4;

        int actualSize = trie.size();

        assertEquals(expectedSize, actualSize);
    }

    private Node get(String key) {
        Node current = root;
        for (char c : key.toCharArray()) {
            if (current == null) {
                break;
            }
            current = current.getNext(c);
        }
        return current;
    }
}