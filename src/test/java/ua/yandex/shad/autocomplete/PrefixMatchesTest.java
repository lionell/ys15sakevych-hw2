package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Trie;
import ua.yandex.shad.tries.Tuple;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    @Mock
    private Trie trie;

    @InjectMocks
    private PrefixMatches prefixMatches = new PrefixMatches();

    private Tuple first = new Tuple("one", 3);
    private Tuple second = new Tuple("apple", 5);

    @Test
    public void testLoad_noParameters_sizeIsZero() {
        int expectedSize = 0;

        int actualSize = prefixMatches.load();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_emptyString_sizeIsZero() {
        int expectedSize = 0;
        String strings = "";

        int actualSize = prefixMatches.load(strings);

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_oneWord_wordAdded() {
        String strings = "one";

        prefixMatches.load(strings);
        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie).add(Matchers.eq(first));
    }

    @Test
    public void testLoad_twoWordsSeparatedWithSpace_addedTwoWords() {
        String strings = "one apple";

        prefixMatches.load(strings);
        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
        verify(trie, times(2)).add(any(Tuple.class));
        verify(trie).add(Matchers.eq(first));
        verify(trie).add(Matchers.eq(second));
    }
}