package finki.ukim.mk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.Arguments;

public class TemperatureAnalyzerTest {

    @Test
    void testInvalidTemperature() {
        int[] temps = {77};
        String result = TemperatureAnalyzer.analyzeTemperatures(temps);
        assertEquals("Invalid temperatures detected.", result);
    }

    @Test
    void testSingleWarmDay() {
        int[] temps = {25};
        String result = TemperatureAnalyzer.analyzeTemperatures(temps);
        assertEquals("All days were warm.", result);
    }

    @Test
    void testMixedTemperatures() {
        int[] temps = {25, 10};
        String result = TemperatureAnalyzer.analyzeTemperatures(temps);
        assertEquals("Some days were warm.", result);
    }

    @Test
    void testEmptyArray() {
        int[] temps = {};
        String result = TemperatureAnalyzer.analyzeTemperatures(temps);
        assertEquals("No warm days.", result);
    }
    @ParameterizedTest
    @MethodSource("temperatureProvider")
    void testAnalyzeTemperaturesParametrized(int[] temps, String expected) {
        assertEquals(expected, TemperatureAnalyzer.analyzeTemperatures(temps));
    }

    static Stream<Arguments> temperatureProvider() {
        return Stream.of(
                // The tests above rewritten
                Arguments.of(new int[]{77}, "Invalid temperatures detected."),
                Arguments.of(new int[]{25}, "All days were warm."),
                Arguments.of(new int[]{25, 10}, "Some days were warm."),
                Arguments.of(new int[]{}, "No warm days."),

                // Edge cases
                Arguments.of(new int[]{20}, "All days were warm."),              // boundary warm
                Arguments.of(new int[]{19}, "No warm days."),                   // just below warm
                Arguments.of(new int[]{-50}, "No warm days."),                  // lower valid boundary
                Arguments.of(new int[]{60}, "All days were warm."),             // upper valid boundary (warm)
                Arguments.of(new int[]{-51}, "Invalid temperatures detected."), // below valid range
                Arguments.of(new int[]{61}, "Invalid temperatures detected."),  // above valid range
                Arguments.of(new int[]{20, 20, 20}, "All days were warm."),     // all warm multiple
                Arguments.of(new int[]{10, 15, 5}, "No warm days."),            // none warm multiple
                Arguments.of(new int[]{20, 10, 30}, "Some days were warm."),    // mixed valid
                Arguments.of(new int[]{20, 70}, "Invalid temperatures detected.") // mix with invalid
        );
    }

}