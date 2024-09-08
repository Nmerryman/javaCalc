package com.hydris.cover;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;


public class Calculator {

    private String toParse;
    private List<Token> tokenList;

    private static 

    Calculator() {}

    public int eval(String s) throws ParsingException {
        toParse = s;
        tokenList = new ArrayList<Token>();

    while (toParse.length() > 0) {
        Token t = new Token();
        toParse = t.parse(toParse);
        tokenList.add(t);
    }
    
    validateTokens();
    createAst();
    
    }

    private validateTokens() throws ParsingException {
        // The only way to be valid is to be alternating between values and ops
        // Also the first and last must be values
        
        TokenKind expecting = TokenKind.value;
 
        for (ListIterator<Token> iter = tokenList.listIterator(); iter.hasNext();) {
            Token t = iter.next();

            if (t.kind != expecting) {
                throw new ParsingException(t.unParsed, "Missmatch between expected value or operator kind.");
            }

            // Update the expected value
            switch (expecting) {
                case TokenKind.value:
                    expecting = TokenKind.operator;
                    break;
                case TokenKind.operator:
                    expecting = TokenKind.value;
                    break;
            }
        }

        // Ensure first and last are valid
        
        if (tokenList.get(0).kind != TokenKind.value) {
            throw new ParsingException(tokenList.get(0).unParsed, "First token is not a valid value.");
        } else if (tokenList.get(tokenList.size() - 1).kind != TokenKind.value) {
            throw new ParsingException(tokenList.get(tokenList.size() - 1).unParsed, "Last token is not a valid value.");
        }
    }
	
    private void createAst() {
        int level = 0;       
    }

}


