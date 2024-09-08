package com.hydris.cover;

public class TokenException extends Exception {
   
    private Token tokenState;

    TokenException(Token value, String message) {
        super(message);
        tokenState = value;
    }

    TokenException(Token value, String message, Throwable cause) {
        super(message, cause);
        tokenState = value;
    }

    public Token getToken() {
        return tokenState;
    }

}


