package com.hydris.cover;

public class TokenException extends Exception {
   
    private Token tokenState;

    TokenException(Token value, String message) {
        super(message);
        tokenState = value;
    }

    TokenException(Token value, String message, Throwable cause) {
        super(message, cause);
        tokenStae = value;
    }

    public String getToken() {
        return tokenState;
    }

}


