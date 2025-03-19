import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import task1.factorial


class TaylorSeriesTest {
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
}