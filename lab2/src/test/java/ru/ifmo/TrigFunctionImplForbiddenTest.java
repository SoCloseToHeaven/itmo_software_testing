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
import ru.ifmo.utils.TrigTableDTO;
import ru.ifmo.reader.CsvReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.ifmo.math.DoubleEquator.ABSOLUTE_ACCURACY;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class TrigFunctionImplForbiddenTest {

    private static final List<TrigTableDTO> invalidValueList = new ArrayList<>();

    @BeforeAll
    static void buildValueList() {
        CsvReader.readDataTrigonometry("trig_test_invalid_values.csv", invalidValueList);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    static Stream<Arguments> provideArguments() {
        return invalidValueList.stream().map(Arguments::of);
    }


    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction2lvlInvalidValues(TrigTableDTO pointDTO) {
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

        assertThrows(ArithmeticException.class, () -> system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction3lvlInvalidValues(TrigTableDTO pointDTO) {
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

        assertThrows(ArithmeticException.class, () -> system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void testFunction4lvlInvalidValues(TrigTableDTO pointDTO) {
        Cos cos = new Cos();

        Sin sin = new Sin(cos);
        Sec sec = new Sec(cos);

        Csc csc = new Csc(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);

        Ln ln = new Ln();

        FunctionImpl system = new FunctionImpl(sec, tan, csc, sin, cos, cot, ln);

        assertThrows(ArithmeticException.class, () -> system.compute(pointDTO.getArgument(), ABSOLUTE_ACCURACY));
    }

}
