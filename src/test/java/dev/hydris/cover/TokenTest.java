package dev.hydris.cover;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;



public class TokenTest {

    @Test
    public void loadUnParsed() {
        Token t = new Token("testing");
        
        assertEquals("testing", t.getUnParsed());
    }

    @Test
    public void parseEmptyString() {
        Token t = new Token();
        Throwable exception = assertThrows(ParsingException.class, () -> t.parse(""));
        
        assertEquals("No text to parse.", exception.getMessage());
    }


    @Test 
    public void tokenizeSingleDigit() throws ParsingException, TokenException {
        Token t = new Token("2");

        t.parse();

        assertEquals(t.kind, TokenKind.value);
        assertEquals(t.getValue(), 2);
    }

    @Test 
    public void expectDigit() throws ParsingException {
        Token t = new Token("1");
        
        t.parse();

        Throwable exception = assertThrows(TokenException.class, () -> t.getOperator());

        assertEquals(exception.getMessage(), "Trying to extract operator from a value token.");
    }

    @Test 
    public void expectOperator() throws ParsingException {
        Token t = new Token("-");

        t.parse();

        Throwable exception = assertThrows(TokenException.class, () -> t.getValue());

        assertEquals(exception.getMessage(), "Trying to extract value from an operator token."); 
    }


    @Test 
    public void tokenizeMultiDigit() throws Exception {
        Token t = new Token("1234");

        t.parse();

        assertEquals(t.kind, TokenKind.value);
        assertEquals(t.getValue(), 1234);
    }

    @Test
    public void tokenizeNegativeDigit() throws Exception {
        Token t = new Token("-1");

        t.parse();

        assertEquals(t.getValue(), -1);
    }

    @Test
    public void removeWhitespace() throws Exception {
        Token t = new Token(" 1 ");

        t.parse();

        assertEquals(t.getValue(), 1);
    }

    @Test
    public void selectSingleDigit() throws Exception{
        Token t = new Token(" 1 2 3 4 - ");

        t.parse();

        assertEquals(t.getValue(), 1);
    }

    @Test
    public void tokenizeOperator() throws Exception{
        Token t = new Token(" + - * /");
        
        t.parse();
        assertEquals(t.getOperator(), "+");
        assertEquals(t.kind, TokenKind.operator);

        t.parse();
        assertEquals(t.getOperator(), "-");

        t.parse();
        assertEquals(t.getOperator(), "*");
        
        t.parse();
        assertEquals(t.getOperator(), "/");
    }

    @Test
    public void tokenizeInvalid() {
        Token t = new Token("a");

        Throwable exception = assertThrows(ParsingException.class, () -> t.parse());
        assertEquals(exception.getMessage(), "Invalid data and can not be parsed.");
    }
}


