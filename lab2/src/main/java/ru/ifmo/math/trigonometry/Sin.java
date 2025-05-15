package ru.ifmo.math.trigonometry;

import lombok.RequiredArgsConstructor;
import ru.ifmo.math.MathFunction;

@RequiredArgsConstructor
public class Sin implements MathFunction {
    private final MathFunction cos;

    @Override
    public double compute(double x, double accuracy) {
        return cos.compute(x - Math.PI / 2, accuracy);
    }
}
