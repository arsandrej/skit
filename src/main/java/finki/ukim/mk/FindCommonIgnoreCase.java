package finki.ukim.mk;

import java.util.*;

public class FindCommonIgnoreCase {

/**
 * Given two lists of strings, return a list of all unique strings that appear in both lists,
 * ignoring case differences.
 *
 * Example:
 * list1 = ["Apple", "banana", "Cherry", "apple"]
 * list2 = ["BANANA", "cherry", "Durian"]
 * Output: ["banana", "Cherry"]
 *
 * The result should contain the matching strings from the first list only,
 * preserving their original casing and order, but without duplicates.
 */
    public static List<String> findCommonIgnoreCase(List<String> list1, List<String> list2) {

        Set<String> lowerList2 = new HashSet<>();
        for (String s : list2) {
            lowerList2.add(s.toLowerCase());
        }

        Set<String> seen = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String s : list1) {

            String lower = s.toLowerCase();

            if (lowerList2.contains(lower) && !seen.contains(lower)) {
                result.add(s);
                seen.add(lower);
            }
        }

        return result;
    }
}