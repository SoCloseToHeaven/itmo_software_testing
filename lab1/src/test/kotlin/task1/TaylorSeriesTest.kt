package task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class TaylorSeriesTest {
    companion object {
        val ACCURACY = 1e-3
    }


    @ParameterizedTest
    @CsvSource(
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "4, 24",
        "5, 120",
        "6, 720"
    )
    fun `check factorial returns correct value`(input: Int, expected: Int) {
        assertEquals(expected, factorial(input))
    }

    @ParameterizedTest
    @CsvSource(
        "-1",
        "-5",
        "-10"
    )
    fun `check factorial throws exception`(input: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            factorial(input)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "-0.5235987755982988, -0.5", // Math.PI / 6
        "0.0, 0.0",
        "0.5235987755982988, 0.5", // Math.PI / 6
        "1.5707963267948966, 1.0", // Math.PI / 2
        "3.665191429188092, -0.5",
        "2.6179938779914944, 0.5",
        "3.141592653589793, 0.0", // Math.PI
        "4.71238898038469, -1.0", // 3 * Math.PI / 2
        "6.283185307179586, 0.0" // 2 * Math.PI
    )
    fun `sin taylor series periodic test`(input: Double, expected: Double) {
        val terms = 9 // Количество членов ряда
        val result = sinTaylorSeries(input, terms)
        assertEquals(expected, result, ACCURACY) // Используем допустимую погрешность
    }
}