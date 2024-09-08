package com.hydris.cover;

import org.apache.commons.lang3.ArrayUtils;



public class Token {

    public TokenKind kind;
    private String operator;
    private Integer value;
    private String unParsed;

    static char[] unaryOperators = {'-'};  // We apply these right away because there is no benifit to holding off
    static char[] binaryOperators = {'+', '-', '*', '/'};



    Token() {
        this("");
    }
    
    Token(String input) {
        unParsed = input;
    }

    public String getUnParsed() {
        return unParsed;
    }

    public String parse(String input) throws ParsingException {
        unParsed = input;
        return parse();
    }

    public String parse() throws ParsingException {

        // Remove all leading empty spaces
        while (unParsed.startsWith(" ")) {
            unParsed = unParsed.substring(1);
        }

        if (unParsed.length() == 0) {
            throw new ParsingException(unParsed, "No text to parse.");
        } 
        // At this point unParsed must be a length of >= 1

        if (unParsed.length() >= 2 && ArrayUtils.contains(unaryOperators, unParsed.charAt(0)) && Character.isDigit(unParsed.charAt(1))) {  // This is the only scenario that a unary works
            parseAsValue();
        } else if (ArrayUtils.contains(binaryOperators, unParsed.charAt(0))) {  // If it's a binary operator, we can easily extract it
            parseAsOperator();
        } else if (Character.isDigit(unParsed.charAt(0))) {
            parseAsValue();
        } else {
            throw new ParsingException(unParsed, "Invalid data and can not be parsed.");
        }

        return unParsed;
        
    }

    private void parseAsValue() {
        int strIndex = 1;

        while (strIndex < unParsed.length() && Character.isDigit(unParsed.charAt(strIndex))) {
            strIndex++;
        }
        
        kind = TokenKind.value;
        value = Integer.valueOf(unParsed.substring(0, strIndex));
        unParsed = unParsed.substring(strIndex);
    }

    private void parseAsOperator() {
        kind = TokenKind.operator;
        value = Integer.valueOf(unParsed.substring(0, 1));
        unParsed = unParsed.substring(1);
    }

    public String getOperator() throws TokenException {
        if (kind != TokenKind.operator) {
            throw new TokenException(this, "Trying to extract operator from a value token.");
        }
        return operator;
    }

    public int getValue() throws TokenException {
        if (kind != TokenKind.value) {
            throw new TokenException(this, "Trying to extract value from an operator token.");
        }
        return value;
    }


}


