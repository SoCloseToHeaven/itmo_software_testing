import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import task1.TaylorSint;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaylorSintTest {

    private static final double ACCURACY = 1e-4;

    @ParameterizedTest
    @CsvSource({
            "0, 0.0, 15",
            "0.5235987756, 0.5, 8", // π/6
            "0.7853981634, 0.7071067812, 8", // π/4
            "1.0471975512, 0.8660254038, 8", // π/3
            "1.5707963268, 1.0, 8", // π/2
            "3.1415926536, 0.0, 8", // π
    })
    void testSin(double input, double expected, int terms) {
        double result = TaylorSint.sin(input, terms);
        assertEquals(expected, result, ACCURACY); // допускаемая погрешность
    }
}
