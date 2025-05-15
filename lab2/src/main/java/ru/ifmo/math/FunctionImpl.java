package ru.ifmo.math;

import lombok.RequiredArgsConstructor;

import static ru.ifmo.math.DoubleEquator.EPSILON;
import static ru.ifmo.math.DoubleEquator.areAlmostEqual;


@RequiredArgsConstructor
public class FunctionImpl implements MathFunction {
    private final MathFunction sec;
    private final MathFunction tan;
    private final MathFunction csc;
    private final MathFunction sin;
    private final MathFunction cos;
    private final MathFunction cot;

    private final MathFunction ln;

    private boolean isZero(double value) {
        return Math.abs(value) < EPSILON;
    }

    @Override
    public double compute(double x, double accuracy) {
        if (x <= 0) {
            double secX = sec.compute(x, accuracy);
            double tanX = tan.compute(x, accuracy);
            double cscX = csc.compute(x, accuracy);
            double sinX = sin.compute(x, accuracy);
            double cosX = cos.compute(x, accuracy);
            double cotX = cot.compute(x, accuracy);

            double denominator = (secX + cotX) + (secX - (cscX + secX));

            // Тут смотрим ниче ли в знаменателях не случилосьТ
            if (isZero(cscX) || isZero(cosX) || isZero(cotX) ||
                    isZero(Math.abs(sinX) - Math.abs(cosX)) ||
                    isZero(secX + cosX) ||
                    isZero(denominator)) {
                throw new ArithmeticException("Division by zero in the computation.");
            }

            double part1 = (((((cscX - sinX) - cosX) * cosX) * tanX) / cscX) / (cosX - (sinX / cotX));
            double part2 = Math.pow(part1, 3) * (cosX * cotX) / secX;
            double part3 = Math.pow(part2, 3) - sinX;
            double part4 = (Math.pow(sinX, 2) * (tanX / Math.pow((secX + cosX), 2))) + secX;
            double part5 = (part3 + part4) * Math.pow(sinX, 2);

            double part6 = Math.pow((cscX - (tanX * cotX)), 3);
            double part7 = sinX / (sinX - (cosX * (cscX * cosX)));
            double part8 = cotX / sinX;
            double part9 = (tanX / secX) + cscX;
            double part10 = cscX / (denominator);
            double part11 = Math.pow(cscX, 3) + (secX - (sinX + cscX));
            double part12 = (part8 / part9) / (Math.pow(part10, 3) * part11);

            double part13 = (part6 - part7) * part12;
            double part14 = (sinX * (cscX * tanX)) / (sinX - cosX);
            double part15 = cotX * Math.pow(part14, 2);

            double result = part5 - (part13 - part15);

            return result;
        }

        return ln.compute(x, accuracy);
    }


}
