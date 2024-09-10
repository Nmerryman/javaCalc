package dev.hydris.cover;

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

    public String errorString() {
        return tokenState.kind + " based on " + tokenState.getUnParsed();
    }

}


