package dev.hydris.cover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class CalculatorTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void basicOne() throws Exception {
    
        Calculator calc = new Calculator();
        Integer result = calc.eval("1");

        assertEquals(result, 1);

    }

    @Test
    public void anotherbasicOne() throws Exception {
        Calculator calc = new Calculator();
        Integer result = calc.eval("2");

        assertEquals(result, 2);
    }
}
