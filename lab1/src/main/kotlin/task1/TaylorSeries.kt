package task1

import kotlin.math.pow

fun sin(x: Double, terms: Int): Double {
    var result = 0.0
    var sign = 1.0

    for (n in 0 until terms) {
        val term = sign * (x.pow((2 * n + 1).toDouble()) / factorial(2 * n + 1))
        result += term
        sign *= -1
    }

    return result
}

fun factorial(n: Int, accumulator: Int = 1): Int = when {
    n < 0 -> throw IllegalArgumentException()
    n == 0 -> accumulator
    else -> factorial(n - 1, n * accumulator)
}
