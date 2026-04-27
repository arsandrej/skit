package finki.ukim.mk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DosageCalculatorTest {
    @ParameterizedTest
    @CsvSource({
            "70, 40.0, false, false, 32.0", // row 6
            "30, 40.0, false, false, 51.0", // row 14
            "30, 40.0, true,  false, 32.0", // row 10
            "70, 60.0, true, false, 79.0", // row 12
            "70, 40.0, false, true,  55.0" // row 5
    })
    void testCalculateDosage(int age, double weight, boolean isHighRisk,
                                 boolean hasAllergy, double expected) {

        double actual = DosageCalculator.calculateDosage(age, weight, isHighRisk, hasAllergy);
        assertEquals(expected, actual, 0.0001);
    }
}
