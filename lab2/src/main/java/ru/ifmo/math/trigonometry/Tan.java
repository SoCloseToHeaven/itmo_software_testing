package ru.ifmo.math.trigonometry;

import lombok.RequiredArgsConstructor;
import ru.ifmo.math.MathFunction;

@RequiredArgsConstructor
public class Tan implements MathFunction {
    private final MathFunction sin;
    private final MathFunction cos;

    @Override
    public double compute(double x, double accuracy) {
        double cosValue = cos.compute(x, accuracy);
        if (Math.abs(cosValue) < accuracy) {
            throw new ArithmeticException("Divided by zero");
        }
        return sin.compute(x, accuracy) / cosValue;
    }

}
