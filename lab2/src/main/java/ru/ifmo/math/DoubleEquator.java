package ru.ifmo.math;

public class DoubleEquator {

    public static final double ABSOLUTE_ACCURACY = 1e-6;

    public static final double EPSILON = 1e-4;

    public static final double RELATIVE_ACCURACY = 1e-2; // Типа процент

    public static boolean areAlmostEqual(double expected, double actual) {
        // Обработка NaN (как они ваще получаются то здесь?? так падажжи)
        if (Double.isNaN(expected) || Double.isNaN(actual)) {
            return Double.isNaN(expected) && Double.isNaN(actual);
        }

        // Если оба числа нули (с учетом погрешности)
        if (Math.abs(expected) <= EPSILON && Math.abs(actual) <= EPSILON) {
            return true;
        }

        double diff = Math.abs(expected - actual);

        // Если числа очень маленькие сравниваем по абсолютной погрешности
        if (Math.abs(expected) < EPSILON) {
            return diff <= EPSILON;
        }

        // В остальных случаях  сравниваем по относительной погрешности
        double relativeError = diff / Math.abs(expected);
        return relativeError <= RELATIVE_ACCURACY;
    }
}
