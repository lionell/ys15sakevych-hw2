package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

    private static Tuple first = new Tuple("one", 1);
    private static Tuple second = new Tuple("two", 2);

    @Test
    public void testLoad_withNoParameters_sizeIsZero() {
        int expectedSize = 0;

        int actualSize = prefixMatches.load();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_withEmptyString_sizeIsZero() {
        int expectedSize = 0;

        int actualSize = prefixMatches.load("");

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_withOneWord_sizeIsOne() {
        int expectedSize = 1;

        int actualSize = prefixMatches.load("one");

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testLoad_withOneWord_containsThisWord() {
        String strings = "one";
        String expectedWord = "one";

        prefixMatches.load(strings);

        assertTrue(prefixMatches.contains(expectedWord));
    }
}