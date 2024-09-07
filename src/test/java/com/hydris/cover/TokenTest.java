package com.hydris.cover;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void tokenizeSingleDigit() throws ParsingException {
        Token t = new Token("2");

        t.parse();

        assertEquals(t.kind, TokenKind.value);
        assertEquals(t.value, "2");
    }

    @Test 
    public void tokenizeMultiDigit() throws Exception {
        Token t = new Token("1234");

        t.parse();

        assertEquals(t.kind, TokenKind.value);
        assertEquals(t.value, "1234");
    }

    @Test
    public void tokenizeNegativeDigit() throws Exception {
        Token t = new Token("-1");

        t.parse();

        assertEquals(t.value, "-1");
    }

    @Test
    public void removeWhitespace() throws Exception {
        Token t = new Token(" 1 ");

        t.parse();

        assertEquals(t.value, "1");
    }

    @Test
    public void selectSingleDigit() throws Exception{
        Token t = new Token(" 1 2 3 4 - ");

        t.parse();

        assertEquals(t.value, "1");
    }

    @Test
    public void tokenizeOperator() throws Exception{
        Token t = new Token(" + - * /");
        
        t.parse();
        assertEquals(t.value, "+");
        assertEquals(t.kind, TokenKind.operator);

        t.parse();
        assertEquals(t.value, "-");

        t.parse();
        assertEquals(t.value, "*");
        
        t.parse();
        assertEquals(t.value, "/");
    }

    @Test
    public void tokenizeInvalid() {
        Token t = new Token("a");

        Throwable exception = assertThrows(ParsingException.class, () -> t.parse());
        assertEquals(exception.getMessage(), "Invalid data and can not be parsed.");
    }
}


