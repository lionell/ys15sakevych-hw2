package ua.yandex.shad.autocomplete;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import ua.yandex.shad.tries.Trie;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    @Mock
    private Trie trie;

    @InjectMocks
    private PrefixMatches prefixMatches = new PrefixMatches();
}