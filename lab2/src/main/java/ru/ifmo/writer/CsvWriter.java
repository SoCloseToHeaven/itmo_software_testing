package ru.ifmo.writer;

import ru.ifmo.math.FunctionImpl;
import ru.ifmo.math.MathFunction;
import ru.ifmo.math.trigonometry.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    private final MathFunction sec;
    private final MathFunction tan;
    private final MathFunction csc;
    private final MathFunction sin;
    private final MathFunction cos;
    private final MathFunction cot;

    private final MathFunction customFunction;

    public CsvWriter(Sec sec, Tan tan, Csc csc,
                     Sin sin, Cos cos, Cot cot,
                     FunctionImpl customFunction) {
        this.sec = sec;
        this.tan = tan;
        this.csc = csc;
        this.sin = sin;
        this.cos = cos;
        this.cot = cot;
        this.customFunction = customFunction;
    }

    public void printCsv(List<Double> arguments, double accuracy) {
        System.out.println("argument,value,cos,sin,tan,cot,sec,csc");

        for (double x : arguments) {
            double cosVal = cos.compute(x, accuracy);
            double sinVal = sin.compute(x, accuracy);
            double tanVal = tan.compute(x, accuracy);
            double cotVal = cot.compute(x, accuracy);
            double secVal = sec.compute(x, accuracy);
            double cscVal = csc.compute(x, accuracy);
            double value = customFunction.compute(x, accuracy);

            System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f%n",
                    x, value, cosVal, sinVal, tanVal, cotVal, secVal, cscVal);
        }
    }

    public void writeToCsv(String filename, MathFunction function, final double start,
                           final double end, final double step)
            throws IOException {

        try (FileWriter writer = new FileWriter("artifacts/"+filename)) {
            for (double i = start; i < end; i+=step) {
                double result;
                try {
                    result = function.compute(i, 0.001);
                } catch (Exception e) {
                    result = Double.NaN;
                }
                writer.write(i + "," + result + "\n");
            }
        }
    }
}