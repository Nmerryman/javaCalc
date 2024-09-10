package dev.hydris.cover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class CalculatorTest {

    private static Calculator calc = new Calculator();

    @Test
    public void basicOne() throws Exception {
    
        Integer result = calc.eval("1");

        assertEquals(result, 1);

    }

    @Test
    public void anotherbasicOne() throws Exception {
        Integer result = calc.eval("2");

        assertEquals(result, 2);
    }

    @Test
    public void calcNothing() {
        Throwable exception = assertThrows(ParsingException.class, () -> calc.eval(""));
    
        assertEquals(exception.getMessage(), "Unable to parse an empty string.");
    }

    @Test
    public void invalid1() throws Exception {
        Throwable exception = assertThrows(ParsingException.class, () -> calc.eval("+1"));

        assertEquals(exception.getMessage(), "First token is not a valid value.");
    }

    @Test 
    public void invalid2() throws Exception {
        Throwable exception = assertThrows(ParsingException.class, () -> calc.eval("1+"));

        assertEquals(exception.getMessage(), "Last token is not a valid value.");
    }

    @Test
    public void invalid3() throws Exception {
        Throwable exception = assertThrows(ParsingException.class, () -> calc.eval("1++2"));

        assertEquals(exception.getMessage(), "Missmatch between expected value or operator kind.");
    }

    @Test
    public void calcMultipleAddition() throws Exception {
        Integer result = calc.eval("1 +2 + 3+ 4");

        assertEquals(result, 10);
    }

    @Test
    public void calcMultipleMultiplication() throws Exception {
        Integer result = calc.eval("1 *2 * 3* 4");

        assertEquals(result, 24);
    }

    @Test
    public void orderOfOperations() throws Exception {
        Integer result = calc.eval("1+2*3");

        assertEquals(result, 7);
    }

    @Test
    public void basicSubtraction() throws Exception {
        Integer result = calc.eval("1-1");

        assertEquals(result, 0);
    }

    @Test
    public void division() throws Exception {
        // Division is a standard floor division
        Integer result = calc.eval("5/2");

        assertEquals(result, 2);
    }

    @Test
    public void allOperators() throws Exception {
        Integer result = calc.eval("1+5-2*6/3");

        assertEquals(result, 2);
    }

    @Test
    public void shuffleNoRoot() throws Exception {
        // Builting the ast requires shuffling things around.
        // A symbol needs to fiil in the and become a new main root.
        Integer result = calc.eval("1*2+3");

        assertEquals(result, 5);
    }

    @Test
    public void shuffleMidPrio() throws Exception {
        // This one needs to find an operator priority edge in the tree and insert itself there.
        Integer result = calc.eval("1*2*3+4*5+6");

        assertEquals(result, 32);
    }
}
