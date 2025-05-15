package ru.ifmo;

import ru.ifmo.math.DoubleEquator;
import ru.ifmo.math.FunctionImpl;
import ru.ifmo.math.logarithm.Ln;
import ru.ifmo.math.trigonometry.*;
import ru.ifmo.writer.CsvWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Application {

    private final static double accuracy = 1e-6;

    public static void main(String[] args) throws IOException {
        Cos cos = new Cos();

        Sin sin = new Sin(cos);
        Tan tan = new Tan(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();

        List<Double> arguments = Arrays.asList(
                -0.2, -0.57, -0.71789, -0.717973, -0.719044, -0.71909, -0.719143,
                -0.83847, -0.92608, -0.956, -1.017685, -1.227, -1.5723, -2.386,
                -2.59385, -2.72, -3.15, -3.85891, -3.85989, -3.86064, -3.86114,
                -3.86132, -3.9939, -4.29005, -4.706, -4.72, -5.07165, -5.167, -5.59
        );


        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        CsvWriter csvWriter = new CsvWriter(sec, tan, csc, sin, cos, cot, system);

        csvWriter.printCsv(arguments, accuracy);
    }

}