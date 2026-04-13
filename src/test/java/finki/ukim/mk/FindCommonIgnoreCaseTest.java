package finki.ukim.mk;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindCommonIgnoreCaseTest {

    // Base Case
    @Test
    void testBaseCase_MultipleMatches() {

        List<String> list1 = Arrays.asList("Apple", "banana", "Cherry", "apple");
        List<String> list2 = Arrays.asList("BANANA", "cherry", "Durian");

        List<String> result = FindCommonIgnoreCase.findCommonIgnoreCase(list1, list2);

        assertEquals(Arrays.asList("banana", "Cherry"), result);
    }

    // list1 size = 1
    @Test
    void testSingleElementList1() {

        List<String> list1 = Arrays.asList("banana");
        List<String> list2 = Arrays.asList("BANANA", "cherry", "Durian");

        List<String> result = FindCommonIgnoreCase.findCommonIgnoreCase(list1, list2);

        assertEquals(Arrays.asList("banana"), result);
    }

    // list2 size = 1
    @Test
    void testSingleElementList2() {

        List<String> list1 = Arrays.asList("Apple", "banana", "Cherry", "apple");
        List<String> list2 = Arrays.asList("BANANA");

        List<String> result = FindCommonIgnoreCase.findCommonIgnoreCase(list1, list2);

        assertEquals(Arrays.asList("banana"), result);
    }

    // no matches
    @Test
    void testNoMatches() {

        List<String> list1 = Arrays.asList("Apple", "Pear", "Watermelon");
        List<String> list2 = Arrays.asList("BANANA", "cherry", "Durian");

        List<String> result = FindCommonIgnoreCase.findCommonIgnoreCase(list1, list2);

        assertTrue(result.isEmpty());
    }

    // one returned element
    @Test
    void testSingleReturnedElement() {

        List<String> list1 = Arrays.asList("Apple", "Pear", "Watermelon");
        List<String> list2 = Arrays.asList("BANANA", "cherry", "apple");

        List<String> result = FindCommonIgnoreCase.findCommonIgnoreCase(list1, list2);

        assertEquals(Arrays.asList("Apple"), result);
    }
}