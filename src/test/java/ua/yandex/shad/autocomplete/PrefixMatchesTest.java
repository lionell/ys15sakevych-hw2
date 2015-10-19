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
    public void testLoad_noParameters_nothingAdded() {
        prefixMatches.load();

        verify(trie, never()).add(any(Tuple.class));
    }

    @Test
    public void testLoad_emptyString_nothingAdded() {
        String strings = "";

        prefixMatches.load(strings);

        verify(trie, never()).add(any(Tuple.class));
    }

    @Test
    public void testLoad_stringWithOneWord_wordAdded() {
        String strings = "one";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie).add(Matchers.eq(first));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithSpace_addedTwoWords() {
        String strings = "one apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
        verify(trie, times(2)).add(any(Tuple.class));
        verify(trie).add(Matchers.eq(first));
        verify(trie).add(Matchers.eq(second));
    }

    @Test
    public void testLoad_stringWithTwoWordsSeparatedWithTab_addedTwoWords() {
        String strings = "one   apple";

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
        verify(trie, times(2)).add(any(Tuple.class));
        verify(trie).add(Matchers.eq(first));
    }

    @Test
    public void testLoad_twoStringsWithOneWordEach_addedTwoWords() {
        String[] strings = {"one", "apple"};

        prefixMatches.load(strings);

        verify(trie, times(1)).add(Matchers.eq(first));
        verify(trie, times(1)).add(Matchers.eq(second));
        verify(trie, times(2)).add(any(Tuple.class));
        verify(trie).add(Matchers.eq(first));
        verify(trie).add(Matchers.eq(second));
    }
}