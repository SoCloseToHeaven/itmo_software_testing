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
        "0.0, 0.0",
        "0.5235987755982988, 0.5", // Math.PI / 6
        "0.7853981633974483, 0.7071067811865475", // Math.PI / 4
        "1.0471975511965976, 0.8660254037844387", // Math.PI / 3
        "1.5707963267948966, 1.0", // Math.PI / 2
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