package ru.ifmo.math;

public class DoubleEquator {

    public static final double ABSOLUTE_ACCURACY = 1e-6;

    public static final int RELATIVE_ACCURACY = 2;

    public static boolean areAlmostEqual(double expected, double actual) {
        // Обработка NaN (как они ваще получаются то здесь?? так падажжи)
        if (Double.isNaN(expected) || Double.isNaN(actual)) {
            return Double.isNaN(expected) && Double.isNaN(actual);
        }

        // Обработка inf
        if (Double.isInfinite(expected) || Double.isInfinite(actual)) {
            return expected == actual;
        }

        // Если оба числа нули (с учетом погрешности)
        if (Math.abs(expected) <= ABSOLUTE_ACCURACY && Math.abs(actual) <= ABSOLUTE_ACCURACY) {
            return true;
        }

        // Разница между числами
        double diff = Math.abs(expected - actual);

        // Если числа очень маленькие сравниваем по абсолютной погрешности
        if (Math.abs(expected) < ABSOLUTE_ACCURACY) {
            return diff <= ABSOLUTE_ACCURACY;
        }

        // В остальных случаях  сравниваем по относительной погрешности
        double relativeError = diff / Math.abs(expected);
        return relativeError <= RELATIVE_ACCURACY;
    }
}
