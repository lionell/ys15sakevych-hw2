package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.Tuple;

import java.util.Iterator;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    @Mock
    private Trie trie;

    @InjectMocks
    private PrefixMatches prefixMatches = new PrefixMatches();

    private Tuple first = new Tuple("one", 3);
    private Tuple second = new Tuple("apple", 5);
    private Tuple third = new Tuple("onedrive", 8);
    private Tuple forth = new Tuple("oneapple", 8);
    private Iterable<String> oneStringIterable = new Iterable<String>() {
        private String[] strings = {"one", "oneapple", "onedrive"};
        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int cur = 0;
                @Override
                public boolean hasNext() {
                    return cur < strings.length;
                }

                @Override
                public String next() {
                    return strings[cur++];
                }
            };
        }
    };

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

        verify(trie, times(1)).add(Matchers.eq(first));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithSpace_addedTwoWords() {
        String strings = "one apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithTab_addedTwoWords() {
        String strings = "one   apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
    }

    @Test
    public void testLoad_twoStringsWithOneWordEach_addedTwoWords() {
        String[] strings = {"one", "apple"};

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
    }

    @Test
    public void testLoad_wordLengthIsLessThanTwoCharacters_nothingAdded() {
        String strings = "on";

        prefixMatches.load(strings);

        verify(trie, never()).add(any(Tuple.class));
    }

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

        verify(trie, times(1)).delete(Matchers.eq(expectedWord));
        verifyNoMoreInteractions(trie);
    }

    /*@Test
    public void testWordsWithPrefix_calledMockedMethod() {
        String word = "apple";
        PrefixMatches prefixMatchesMock = mock(PrefixMatches.class);
        when(prefixMatchesMock.wordsWithPrefix(Matchers.eq("apple"), Matchers.eq(3))).thenReturn(null);
        when(prefixMatchesMock.wordsWithPrefix(Matchers.eq("apple"))).thenCallRealMethod();
        String expectedWord = "apple";
        int expectedK = 3;

        prefixMatchesMock.wordsWithPrefix(word);

        verify(prefixMatchesMock, times(1)).wordsWithPrefix(expectedWord, expectedK);
    }*/

    private String generate(Iterable<String> generator) {
        Iterator<String> iterator = generator.iterator();
        String result = iterator.next();
        while(iterator.hasNext()) {
            result += " " + iterator.next();
        }
        return result;
    }

    @Test
    public void testWordsWithPrefix_kEqualsOne_result() {
        String pref = "one";
        String expectedResult = "one";
        int k = 1;
        when(trie.wordsWithPrefix(Matchers.eq("one"))).thenReturn(oneStringIterable);

        String actualResult = generate(prefixMatches.wordsWithPrefix(pref, k));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordsWithPrefix_kEqualsTwo_result() {
        String pref = "one";
        String expectedResult = "one oneapple onedrive";
        int k = 2;
        when(trie.wordsWithPrefix(Matchers.eq("one"))).thenReturn(oneStringIterable);

        String actualResult = generate(prefixMatches.wordsWithPrefix(pref, k));

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsWithPrefix_prefixIsLessThenTwoSymbols_exceptionThrown() {
        String pref = "on";
        int k = 2;
        when(trie.wordsWithPrefix(Matchers.eq("one"))).thenReturn(oneStringIterable);
        prefixMatches.wordsWithPrefix(pref, k);
    }

    @Test
    public void testSize_result() {
        when(trie.size()).thenReturn(13);
        int expectedSize = 13;

        int actualSize = prefixMatches.size();

        assertEquals(expectedSize, actualSize);
    }
}