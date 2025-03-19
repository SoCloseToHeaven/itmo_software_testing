package task1

import kotlin.math.abs
import kotlin.math.pow

fun sinTaylorSeries(x: Double, terms: Int): Double {
    val resultSign = if (x >= 0) 1.0 else -1.0
    val normalizedX = abs(x) % (2 * Math.PI)

    val adjustedX = when {
        (normalizedX > Math.PI / 2) and (normalizedX <= Math.PI) -> Math.PI - normalizedX
        (normalizedX > Math.PI) and (normalizedX <= 3 * Math.PI / 2) -> Math.PI - normalizedX
        (normalizedX > 3 * Math.PI / 2) and (normalizedX <= 2 * Math.PI) -> normalizedX - 2 * Math.PI
        else -> normalizedX
    }

    var result = 0.0
    var sign = 1.0

    for (n in 0 until terms) {
        val term = sign * (adjustedX.pow((2 * n + 1).toDouble()) / factorial(2 * n + 1))
        result += term
        sign *= -1
    }

    return result * resultSign
}

fun factorial(n: Int, accumulator: Int = 1): Int = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 -> accumulator
    else -> factorial(n - 1, n * accumulator)
}
