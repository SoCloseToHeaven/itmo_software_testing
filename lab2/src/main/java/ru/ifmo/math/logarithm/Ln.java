package ru.ifmo.math.logarithm;

import ru.ifmo.math.MathFunction;

public class Ln implements MathFunction {
    @Override
    public double compute(double x, double accuracy) {
        if (x < accuracy) throw new ArithmeticException("Logarithm from zero");
        final double xx = (1 - x) / (1 + x);
        double acc = 0;
        int pow = 1;
        double p1 = Double.MAX_VALUE;
        double p2 = 0;
        while (Math.abs(p1 - p2) > accuracy) {
            acc += p2;
            p1 = p2;
            p2 = -2 * Math.pow(xx, pow) / pow;
            pow += 2;
        }
        return acc;
    }
}
