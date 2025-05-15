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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static ru.ifmo.math.DoubleEquator.ABSOLUTE_ACCURACY;
import static ru.ifmo.math.DoubleEquator.areAlmostEqual;

public class LogFunctionImplTest {
    private static final List<LogTableDTO> valueList = new ArrayList<>();

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
}
