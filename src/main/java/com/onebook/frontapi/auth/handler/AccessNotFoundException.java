package com.onebook.frontapi.auth.handler;

public class AccessNotFoundException extends RuntimeException {
    public AccessNotFoundException(String message) {
        super(message);
    }
}
