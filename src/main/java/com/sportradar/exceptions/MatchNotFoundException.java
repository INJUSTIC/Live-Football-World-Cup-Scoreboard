package com.sportradar.exceptions;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String message) {
        super(message);
    }
}
