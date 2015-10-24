package ua.yandex.shad.collections;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Test structure
 *
 * /@Test
 * public void test[UnitOfWork_StateUnderTest_ExpectedBehavior]() {
 *     // Arrange
 *     ...
 *     // Act
 *     ...
 *     // Assert
 * }
 */

public class StringArrayTest {

    //<editor-fold desc="Tests for StringArray(int capacity)">
    @Test(expected = IllegalArgumentException.class)
    public void testCapacityConstructor_negativeCapacity_exception() {
        int capacity = -1;

        new StringArray(capacity);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for StringArray()">
    @Test
    public void testDefaultConstructor_size() {
        int expectedSize = 0;

        StringArray array = new StringArray();
        int actualSize = array.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testDefaultConstructor_capacity() {
        int expectedCapacity = 10;

        StringArray array = new StringArray();
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for StringArray(String[] values)">
    @Test
    public void testStringsConstructor_size() {
        String[] strings = {"one", "two", "three"};
        int expectedSize = 3;

        StringArray array = new StringArray(strings);
        int actualSize = array.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testStringsConstructor_capacity() {
        String[] strings = {"one", "two", "three"};
        int expectedCapacity = 3;

        StringArray array = new StringArray(strings);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    public void testStringsConstructor_modifyStringsArrayAfter() {
        String[] strings = {"one", "two", "three"};
        String[] expectedStrings = {"one", "two", "three"};

        StringArray array = new StringArray(strings);
        strings[0] = "four";
        String[] actualStrings = array.toArray();

        assertArrayEquals(expectedStrings, actualStrings);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for toArray()">
    @Test
    public void testToArray_emptyArray() {
        String[] strings = {};
        String[] expectedStrings = {};

        StringArray array = new StringArray(strings);
        String[] actualStrings = array.toArray();

        assertArrayEquals(expectedStrings, actualStrings);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for get(int index)">
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_emptyArray() {
        String[] strings = {};
        int index = 0;

        StringArray array = new StringArray(strings);
        array.get(index);
    }

    @Test
    public void testGet() {
        String[] strings = {"one", "two", "three"};
        int index = 1;
        String expectedResult = "two";

        StringArray array = new StringArray(strings);
        String actualResult = array.get(index);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_negativeIndex() {
        String[] strings = {"one", "two", "three"};
        int index = -2;

        StringArray array = new StringArray(strings);
        array.get(index);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_indexGreaterThanSizeOfArray() {
        String[] strings = {"one", "two", "three"};
        int index = 7;

        StringArray array = new StringArray(strings);
        array.get(index);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for set(int index, String value)">
    @Test
    public void testSet() {
        String[] strings = {"one", "two", "three"};
        int index = 1;
        String newValue = "four";
        String[] expectedArray = {"one", "four", "three"};

        StringArray array = new StringArray(strings);
        array.set(index, newValue);
        String[] actualArray = array.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_negativeIndex() {
        String[] strings = {"one", "two", "three"};
        int index = -7;
        String newValue = "four";

        StringArray array = new StringArray(strings);
        array.set(index, newValue);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSet_indexGreaterThanSizeOfArray() {
        String[] strings = {"one", "two", "three"};
        int index = 4;
        String newValue = "four";

        StringArray array = new StringArray(strings);
        array.set(index, newValue);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for ensureCapacity(int minCapacity)">
    @Test
    public void testEnsureCapacity_minCapacityIsLessThanCurrent() {
        String[] strings = {"one", "two", "three", "four"};
        int minCapacity = 3;
        int expectedCapacity = 4;

        StringArray array = new StringArray(strings);
        array.ensureCapacity(minCapacity);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    public void testEnsureCapacity_minCapacityIsLessThanDoubledCurrent() {
        String[] strings = {"one", "two", "three", "four"};
        int minCapacity = 5;
        int expectedCapacity = 8;

        StringArray array = new StringArray(strings);
        array.ensureCapacity(minCapacity);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    public void testEnsureCapacity_minCapacityIsMoreThanDoubledCurrent() {
        String[] strings = {"one", "two", "three", "four"};
        int minCapacity = 15;
        int expectedCapacity = 15;

        StringArray array = new StringArray(strings);
        array.ensureCapacity(minCapacity);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for add(String value)">
    @Test
    public void testAdd_arraySize() {
        String[] strings = {"one", "two", "three", "four"};
        String newValue = "five";
        int expectedSize = 5;

        StringArray array = new StringArray(strings);
        array.add(newValue);
        int actualSize = array.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAdd_arrayCapacity() {
        String[] strings = {"one", "two", "three", "four"};
        String newValue = "five";
        int expectedCapacity = 8;

        StringArray array = new StringArray(strings);
        array.add(newValue);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    public void testAdd_array() {
        String[] strings = {"one", "two", "three", "four"};
        String newValue = "five";
        String[] expectedArray = {"one", "two", "three", "four", "five"};

        StringArray array = new StringArray(strings);
        array.add(newValue);
        String[] actualArray = array.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for add(String[] values)">
    @Test
    public void testAddRange_emptyArray_newArray() {
        String[] strings = {"one", "two", "three", "four"};
        String[] newStrings = {};
        String[] expectedArray = {"one", "two", "three", "four"};

        StringArray array = new StringArray(strings);
        array.add(newStrings);
        String[] actualArray = array.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testAddRange_someArray_newArraySize() {
        String[] strings = {"one", "two", "three", "four"};
        String[] newStrings = {"five", "six", "seven"};
        int expectedSize = 7;

        StringArray array = new StringArray(strings);
        array.add(newStrings);
        int actualSize = array.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAddRange_someArray_newArrayCapacity() {
        String[] strings = {"one", "two", "three", "four"};
        String[] newStrings = {"five", "six", "seven"};
        int expectedCapacity = 8;

        StringArray array = new StringArray(strings);
        array.add(newStrings);
        int actualCapacity = array.capacity();

        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    public void testAddRange_someArray_newArray() {
        String[] strings = {"one", "two", "three", "four"};
        String[] newStrings = {"five", "six", "seven"};
        String[] expectedArray = {"one", "two", "three", "four", "five", "six", "seven"};

        StringArray array = new StringArray(strings);
        array.add(newStrings);
        String[] actualArray = array.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for isEmpty()">
    @Test
    public void testIsEmpty_emptyArray_true() {
        StringArray array = new StringArray();
        boolean actualResult = array.isEmpty();

        assertTrue(actualResult);
    }

    @Test
    public void testIsEmpty_notEmptyArray_false() {
        String[] strings = {"one", "two"};

        StringArray array = new StringArray(strings);
        boolean actualResult = array.isEmpty();

        assertFalse(actualResult);
    }
    //</editor-fold>

    //<editor-fold desc="Tests for iterator()">
    @Test
    public void testIterator_emptyArray_negativeHasNext() {
        String[] strings = {};

        StringArray array = new StringArray(strings);
        Iterator<String> iterator = array.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator_emptyArray_nextShouldThrowException() {
        String[] strings = {};

        StringArray array = new StringArray(strings);
        Iterator<String> iterator = array.iterator();

        iterator.next();
    }

    @Test
    public void testIterator_singleElementArray_result() {
        String[] strings = {"one"};
        String expectedWord = "one";

        StringArray array = new StringArray(strings);
        Iterator<String> iterator = array.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(expectedWord, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator_arrayWithSeveralElements_result() {
        String[] strings = {"one", "two", "three"};
        String expectedWord1 = "one";
        String expectedWord2 = "two";
        String expectedWord3 = "three";

        StringArray array = new StringArray(strings);
        Iterator<String> iterator = array.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(expectedWord1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(expectedWord2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(expectedWord3, iterator.next());
        assertFalse(iterator.hasNext());
    }
    //</editor-fold>
}