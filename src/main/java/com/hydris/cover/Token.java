package com.hydris.cover;

import org.apache.commons.lang3.ArrayUtils;



public class Token {

    public TokenKind kind;
    public String value;
    private String unParsed;

    static char[] unaryOperators = {'-'};
    static char[] binaryOperators = {'+', '-', '*', '/'};



    Token() {
        this("");
    }
    
    Token(String input) {
        unParsed = input;
    }

    public void parse(String input) throws ParsingException {
        unParsed = input;
        parse();
    }

    public void parse() throws ParsingException {

        // Remove all leading empty spaces
        while (unParsed.startsWith(" ")) {
            unParsed = unParsed.substring(1);
        }

        if (unParsed.length() == 0) {
            throw new ParsingException(unParsed, "No text to parse.");
        } 

        if (unParsed.length() >= 2 && ArrayUtils.contains(unaryOperators, unParsed.charAt(0)) && Character.isDigit(unParsed.charAt(1))) {
            
        }
        

    }

}


