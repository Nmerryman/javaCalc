package com.hydris.cover;

public class ParsingException extends Exception {
   
    private String errorString;

    ParsingException(String value, String message) {
        super(message);
        errorString = value;
    }

    ParsingException(String value, String message, Throwable cause) {
        super(message, cause);
        errorString = value;
    }

    public String getErrorString() {
        return errorString;
    }

}


