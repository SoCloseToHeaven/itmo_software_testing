package ru.ifmo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ifmo.math.DoubleEquator;
import ru.ifmo.math.FunctionImpl;
import ru.ifmo.math.logarithm.Ln;
import ru.ifmo.math.trigonometry.*;
import ru.ifmo.utils.TrigTableDTO;
import ru.ifmo.reader.CsvReader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static ru.ifmo.math.DoubleEquator.ABSOLUTE_ACCURACY;
import static ru.ifmo.math.DoubleEquator.areAlmostEqual;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class TrigFunctionImplTest {

    private static final List<TrigTableDTO> valueList = new ArrayList<>();

    @BeforeAll
    static void buildValueList() {
        CsvReader.readDataTrigonometry("trig_test_values.csv", valueList);
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
    void testFunction1lvl(TrigTableDTO pointDTO) {
        Cos cos = Mockito.mock(Cos.class);

        Sin sin = Mockito.mock(Sin.class);
        Sec sec = Mockito.mock(Sec.class);

        Csc csc = Mockito.mock(Csc.class);

        Tan tan = Mockito.mock(Tan.class);
        Cot cot = Mockito.mock(Cot.class);

        Ln ln = new Ln();

        when(cos.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getCosValue());
        when(sin.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getSinValue());
        when(sec.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getSecValue());
        when(csc.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getCscValue());
        when(tan.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getTanValue());
        when(cot.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getCotValue());

        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        double result = system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY);

        assertTrue(areAlmostEqual(pointDTO.getTargetValue(), result));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction2lvl(TrigTableDTO pointDTO) {
        Cos cos = Mockito.mock(Cos.class);

        Sin sin = Mockito.mock(Sin.class);
        Sec sec = Mockito.mock(Sec.class);

        Csc csc = new Csc(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();

        when(cos.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getCosValue());
        when(sin.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getSinValue());
        when(sec.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getSecValue());

        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        double result = system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY);

        assertTrue(areAlmostEqual(pointDTO.getTargetValue(), result));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction3lvl(TrigTableDTO pointDTO) {
        Cos cos = Mockito.mock(Cos.class);

        Sin sin = new Sin(cos);
        Sec sec = new Sec(cos);

        Csc csc = new Csc(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();

        when(cos.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getCosValue());
        when(cos.compute(pointDTO.getArgument() - Math.PI / 2, ABSOLUTE_ACCURACY)).thenAnswer(invocation -> pointDTO.getSinValue());

        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        double result = system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY);

        assertTrue(areAlmostEqual(pointDTO.getTargetValue(), result));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction4lvl(TrigTableDTO pointDTO) {
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
