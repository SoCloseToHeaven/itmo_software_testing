package ru.ifmo.math.trigonometry;

import lombok.RequiredArgsConstructor;
import ru.ifmo.math.MathFunction;

@RequiredArgsConstructor
public class Csc implements MathFunction {
    private final MathFunction sin;

    @Override
    public double compute(double x, double accuracy) {
        double sinValue = sin.compute(x, accuracy);
        if (Math.abs(sinValue) < accuracy){
            throw new ArithmeticException("Divided by zero");
        }
        return 1 / sinValue;
    }
}
