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
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Iterator;
import ua.yandex.shad.utils.Iterables;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.Tuple;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

    //<editor-fold desc="Set up tests">
    @Mock private Trie trie;

    @InjectMocks private final PrefixMatches prefixMatches = new PrefixMatches();

    private final Tuple oneTuple = new Tuple("one", 3);
    private final Tuple appleTuple = new Tuple("apple", 5);
    private final Tuple oneDriveTuple = new Tuple("onedrive", 8);
    private final Tuple oneAppleTuple = new Tuple("oneapple", 8);
    private final Iterable<String> oneStringIterable = new Iterable<String>() {
        private String[] strings = {"one", "oneapple", "onedrive"};
        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int current = 0;
                @Override
                public boolean hasNext() {
                    return current < strings.length;
                }

                @Override
                public String next() {
                    return strings[current++];
                }
            };
        }
    };
    //</editor-fold>

    //<editor-fold desc="Tests for load(String... strings)">
    @Test
    public void testLoad_noParameters_nothingAdded() {
        prefixMatches.load();

        verify(trie, never()).add(Matchers.any(Tuple.class));
    }

    @Test
    public void testLoad_emptyString_nothingAdded() {
        String strings = "";

        prefixMatches.load(strings);

        verify(trie, never()).add(Matchers.any(Tuple.class));
    }

    @Test
    public void testLoad_stringWithOneWord_wordAdded() {
        String strings = "one";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(eq(oneTuple));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithSpace_addedTwoWords() {
        String strings = "one apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(eq(oneTuple));
        verify(trie, times(1)).add(eq(appleTuple));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithTab_addedTwoWords() {
        String strings = "one   apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(eq(oneTuple));
        verify(trie, times(1)).add(eq(appleTuple));
    }

    @Test
    public void testLoad_twoStringsWithOneWordEach_addedTwoWords() {
        String[] strings = {"one", "apple"};

        prefixMatches.load(strings);

        verify(trie, times(1)).add(eq(oneTuple));
        verify(trie, times(1)).add(eq(appleTuple));
    }

    @Test
    public void testLoad_wordLengthIsLessThanTwoCharacters_nothingAdded() {
        String strings = "on";

        prefixMatches.load(strings);

        verify(trie, never()).add(any(Tuple.class));
    }
    //</editor-fold>

    //<editor-fold desc="Tests for contains(String word)">
    @Test
    public void testContains_hitWord_positiveResult() {
        String word = "one";
        when(trie.contains("one")).thenReturn(true);

        assertTrue(prefixMatches.contains(word));
    }

    @Test
    public void testContains_missWord_negativeResult() {
        String word = "one";
        when(trie.contains("one")).thenReturn(false);

        assertFalse(prefixMatches.contains(word));
    }
    //</editor-fold>

    //<editor-fold desc="Tests for delete(String word)">
    @Test
    public void testDelete_hitWord_positiveResult() {
        String word = "one";
        when(trie.delete("one")).thenReturn(true);

        assertTrue(prefixMatches.delete(word));
    }

    @Test
    public void testDelete_missWord_negativeResult() {
        String word = "one";
        when(trie.delete("one")).thenReturn(false);

        assertFalse(prefixMatches.delete(word));
    }

    @Test
    public void testDelete_hitWord_deletedWord() {
        String word = "one";
        String expectedWord = "one";

        prefixMatches.delete(word);

        verify(trie, times(1)).delete(eq(expectedWord));
        verifyNoMoreInteractions(trie);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for wordsWithPrefix(String pref)">
    @Test
    public void testWordsWithPrefix_result() {
        String pref = "one";
        String expectedResult = "one oneapple onedrive";
        when(trie.wordsWithPrefix(eq("one"))).thenReturn(oneStringIterable);

        String actualResult = new Iterables<String>().toString(prefixMatches.wordsWithPrefix(pref));

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_prefixIsLessThenTwoSymbols_exceptionThrown() {
        String pref = "on";
        when(trie.wordsWithPrefix(eq("one"))).thenReturn(oneStringIterable);
        prefixMatches.wordsWithPrefix(pref);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for wordsWithPrefix(String pref, int k)">
    @Test
    public void testWordsWithPrefixWithK_kEqualsOne_result() {
        String pref = "one";
        String expectedResult = "one";
        int k = 1;
        when(trie.wordsWithPrefix(eq("one"))).thenReturn(oneStringIterable);

        String actualResult = new Iterables<String>().toString(prefixMatches.wordsWithPrefix(pref, k));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefixWithK_kEqualsTwo_result() {
        String pref = "one";
        String expectedResult = "one oneapple onedrive";
        int k = 2;
        when(trie.wordsWithPrefix(eq("one"))).thenReturn(oneStringIterable);

        String actualResult = new Iterables<String>().toString(prefixMatches.wordsWithPrefix(pref, k));

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefixWithK_prefixIsLessThenTwoSymbols_exceptionThrown() {
        String pref = "on";
        int k = 2;
        when(trie.wordsWithPrefix(eq("one"))).thenReturn(oneStringIterable);
        prefixMatches.wordsWithPrefix(pref, k);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for size()">
    @Test
    public void testSize_result() {
        when(trie.size()).thenReturn(13);
        int expectedSize = 13;

        int actualSize = prefixMatches.size();

        assertEquals(expectedSize, actualSize);
    }
    //</editor-fold>
}