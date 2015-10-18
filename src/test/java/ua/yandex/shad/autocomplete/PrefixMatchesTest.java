package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
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
    public void testLoad_withNoParameters_sizeIsZero() {
        int expectedSize = 0;

        int actualSize = prefixMatches.load();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_withEmptyString_sizeIsZero() {
        int expectedSize = 0;
        String strings = "";

        int actualSize = prefixMatches.load(strings);

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_withOneWord_addedOneWord() {
        String strings = "one";

        prefixMatches.load(strings);
        verify(trie, times(1)).add(any(Tuple.class));
    }

    @Test
    public void testLoad_withOneWord_addedOddWord() {
        String strings = "one";

        prefixMatches.load(strings);
        verify(trie).add(Matchers.eq(first));
    }
}