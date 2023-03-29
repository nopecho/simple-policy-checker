package com.nopecho.policy.domain.exception;

public class PolicyException extends RuntimeException {

    private String message;

    public PolicyException(String message) {
        super(message);
    }
}
