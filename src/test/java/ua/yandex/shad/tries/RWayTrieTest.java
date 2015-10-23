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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ua.yandex.shad.tries.RWayTrie.Node;
import static ua.yandex.shad.utils.StringIterableUtils.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.collections.Tuple;

@RunWith(MockitoJUnitRunner.class)
public class RWayTrieTest {

    //<editor-fold desc="Set up tests">
    @Mock private Tuple oneMock;
    @Mock private Tuple appleMock;
    @Mock private Tuple oneDriveMock;
    @Mock private Tuple oneAppleMock;

    private final RWayTrie trie = new RWayTrie();
    private final Node root = trie.getRoot();

    @Before
    public void setUp() {
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
    //</editor-fold>

    //<editor-fold desc="Tests for add(Tuple t)">
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
    //</editor-fold>

    //<editor-fold desc="Tests for contains(String word)">
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
    //</editor-fold>

    //<editor-fold desc="Tests for delete(String word)">
    @Test
    public void testDelete_hitWord_positiveResult() {
        String word = "oneapple";

        assertTrue(trie.delete(word));
    }

    @Test
    public void testDelete_hitWord_sizeDecreased() {
        String word = "oneapple";
        int expectedSize = 3;

        trie.delete(word);
        int actualSize = trie.size();

        assertEquals(expectedSize, actualSize);
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
    //</editor-fold>

    //<editor-fold desc="Tests for words()">
    @Test
    public void testWords_result() {
        String[] expectedResult = {"o", "on", "one", "oneapple"};

        String[] actualResult = toArray(trie.words());

        assertArrayEquals(expectedResult, actualResult);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for wordsWithPrefix(String pref)">
    @Test
    public void testWordsWithPrefix_hitsSeveralWords_result() {
        String pref = "one";
        String[] expectedResult = {"one", "oneapple"};

        String[] actualResult = toArray(trie.wordsWithPrefix(pref));

        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_hitsOneWord_result() {
        String pref = "oneapp";
        String[] expectedResult = {"oneapple"};

        String[] actualResult = toArray(trie.wordsWithPrefix(pref));

        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_doNotHitAnyWord_negativeResultIteratorHasNext() {
        String pref = "two";

        assertFalse(trie.wordsWithPrefix(pref).iterator().hasNext());
    }
    //</editor-fold>

    //<editor-fold desc="Tests for size()">
    @Test
    public void testSize_result() {
        int expectedSize = 4;

        int actualSize = trie.size();

        assertEquals(expectedSize, actualSize);
    }
    //</editor-fold>

    //<editor-fold desc="Some additional methods">
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
    //</editor-fold>
}