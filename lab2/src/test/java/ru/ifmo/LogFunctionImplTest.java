package ru.ifmo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.math.FunctionImpl;
import ru.ifmo.math.logarithm.Ln;
import ru.ifmo.math.trigonometry.*;
import ru.ifmo.utils.LogTableDTO;
import ru.ifmo.reader.CsvReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class LogFunctionImplTest {
    private static final List<LogTableDTO> valueList = new ArrayList<>();

    private static final double ABSOLUTE_ACCURACY = 1e-4;

    private static final int RELATIVE_ACCURACY = 2;

    private static final Map<Double, Double>  normalLogValues = new HashMap<>();

    @BeforeAll
    static void buildValueList() {
        CsvReader.readDataLogarithm("log_test_values.csv", valueList);
        normalLogValues.put(10.0, 2.302585092994046);
        normalLogValues.put(2.0, 0.6931471805599453);
        normalLogValues.put(3.0, 1.0986122886681098);
        normalLogValues.put(5.0, 1.6094379124341003);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    static Stream<Arguments> provideArguments() {
        return valueList.stream().map(Arguments::of);
    }


    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction1lvl(LogTableDTO pointDTO) {
        Cos cos = new Cos();

        Sin sin = new Sin(cos);
        Sec sec = new Sec(cos);

        Csc csc = new Csc(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = Mockito.mock(Ln.class);

        when(ln.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getLnValue());
        when(ln.compute(10, ABSOLUTE_ACCURACY)).thenReturn(normalLogValues.get(10.0));
        when(ln.compute(2, ABSOLUTE_ACCURACY)).thenReturn(normalLogValues.get(2.0));
        when(ln.compute(3, ABSOLUTE_ACCURACY)).thenReturn(normalLogValues.get(3.0));
        when(ln.compute(5, ABSOLUTE_ACCURACY)).thenReturn(normalLogValues.get(5.0));


        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        double result = system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY);

        assertTrue(areAlmostEqual(pointDTO.getTargetValue(), result));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction2lvl(LogTableDTO pointDTO) {
        Cos cos = new Cos();

        Sin sin = new Sin(cos);
        Sec sec = new Sec(cos);

        Csc csc = new Csc(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();

        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        double result = system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY);

        assertTrue(areAlmostEqual(pointDTO.getTargetValue(), result));
    }

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
